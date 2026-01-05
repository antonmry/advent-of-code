use std::{
    collections::VecDeque,
    fs::File,
    io::{BufRead, BufReader},
};

struct Machine {
    lights: u16,
    buttons: Vec<Vec<u16>>,
    binary_buttons: Vec<u16>,
    joltage_reqs: Vec<u16>,
}

fn main() -> Result<(), Box<dyn std::error::Error>> {
    let file = File::open("./input")?;
    let reader = BufReader::new(file);

    let machines = parse_input(reader);

    let result: usize = machines.iter().map(count_buttons).sum();
    println!("Result part 1: {result}");
    assert_eq!(475, result);

    let mut result = 0;
    for machine in &machines {
        result += find_joltage(machine)?;
    }
    println!("Result part 2: {result}");
    assert_eq!(18273, result);

    Ok(())
}

// Breadth-first search algorithm
// We could track already visited states with a HashSet to make it more efficient
fn count_buttons(machine: &Machine) -> usize {
    let mut queue: VecDeque<(u16, usize)> = VecDeque::new();
    queue.push_back((machine.lights, 0));

    loop {
        let (state, depth) = queue.pop_front().unwrap();

        if state == 0 {
            return depth;
        }

        for button in &machine.binary_buttons {
            queue.push_back((state ^ button, depth + 1));
        }
    }
}

fn find_joltage(machine: &Machine) -> Result<usize, Box<dyn std::error::Error>> {
    use z3::ast::Ast;
    use z3::{Config, Context, Optimize, SatResult};

    let n_positions = machine.joltage_reqs.len();
    let n_buttons = machine.buttons.len();

    // Create Z3 context and optimizer
    let cfg = Config::new();
    let ctx = Context::new(&cfg);
    let opt = Optimize::new(&ctx);

    // Create integer variables for each button (number of presses)
    let button_vars: Vec<_> = (0..n_buttons)
        .map(|i| z3::ast::Int::new_const(&ctx, format!("x{}", i)))
        .collect();

    // Add non-negativity constraints: x_i >= 0
    let zero = z3::ast::Int::from_i64(&ctx, 0);
    for var in &button_vars {
        opt.assert(&var.ge(&zero));
    }

    // Add constraints for each position: sum of button presses covering it = target
    for pos in 0..n_positions {
        let target = z3::ast::Int::from_i64(&ctx, machine.joltage_reqs[pos] as i64);

        // Find all buttons covering this position
        let covering_buttons: Vec<&z3::ast::Int> = machine
            .buttons
            .iter()
            .enumerate()
            .filter(|(_, button)| button.contains(&(pos as u16)))
            .map(|(btn_idx, _)| &button_vars[btn_idx])
            .collect();

        if covering_buttons.is_empty() {
            if machine.joltage_reqs[pos] != 0 {
                return Err("Position not covered by any button".into());
            }
            continue;
        }

        // Build sum of covering buttons
        let sum = if covering_buttons.len() == 1 {
            covering_buttons[0].clone()
        } else {
            z3::ast::Int::add(&ctx, &covering_buttons)
        };

        opt.assert(&sum._eq(&target));
    }

    // Minimize total button presses
    let refs: Vec<&z3::ast::Int> = button_vars.iter().collect();
    let total = z3::ast::Int::add(&ctx, &refs);
    opt.minimize(&total);

    // Solve
    match opt.check(&[]) {
        SatResult::Sat => {
            let model = opt.get_model().ok_or("No model found")?;
            let result: i64 = button_vars
                .iter()
                .map(|var| model.eval(var, true).unwrap().as_i64().unwrap())
                .sum();
            Ok(result as usize)
        }
        SatResult::Unsat => Err("No solution exists".into()),
        SatResult::Unknown => Err("Solver returned unknown".into()),
    }
}

fn parse_input<R: BufRead>(reader: R) -> Vec<Machine> {
    let mut machines: Vec<Machine> = Vec::new();

    for l in reader.lines() {
        let mut lights: u16 = 0;
        let line = l.unwrap();
        let (line1, line2) = line.split_once("]").unwrap();

        line1.chars().rev().for_each(|li| {
            match li {
                '.' => lights >>= 1,
                '#' => lights = (lights >> 1) | (1 << 15),
                _ => {} // Ignore other chars like [
            }
        });

        let binary_buttons: Vec<u16> = line2
            .split('(')
            .skip(1)
            .map(|b| {
                b.split_once(')')
                    .unwrap()
                    .0
                    .split(',')
                    .map(|c| 1u16 << (15 - c.parse::<u16>().unwrap()))
                    .fold(0, |acc, x| acc ^ x)
            })
            .collect();

        let mut buttons: Vec<Vec<u16>> = line2
            .split('(')
            .skip(1)
            .map(|b| {
                b.split_once(')')
                    .unwrap()
                    .0
                    .split(',')
                    .map(|c| c.parse::<u16>().unwrap())
                    .collect()
            })
            .collect();

        // This is the key: bigger buttons increase the joltage with less pushes
        // so we prioritize them over the smaller ones reducing the number of potential
        // combinations
        buttons.sort_by_key(|a| std::cmp::Reverse(a.len()));

        let joltage_reqs: Vec<u16> = line2
            .split('{')
            .skip(1)
            .flat_map(|b| {
                b.split_once('}')
                    .unwrap()
                    .0
                    .split(',')
                    .map(|c| c.parse::<u16>().unwrap())
            })
            .collect();

        machines.push(Machine {
            lights,
            buttons,
            binary_buttons,
            joltage_reqs,
        });
    }

    machines
}

#[cfg(test)]
mod tests {
    use std::io::Cursor;

    use super::*;

    #[test]
    fn default() -> Result<(), Box<dyn std::error::Error>> {
        let input = "[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
[...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}
[.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}";
        let reader = Cursor::new(input);

        let machines = parse_input(reader);
        let result = machines.iter().map(count_buttons).sum();
        assert_eq!(7_usize, result);

        let mut result = 0;
        for machine in &machines {
            result += find_joltage(machine)?;
        }

        assert_eq!(33_usize, result);
        Ok(())
    }
}
