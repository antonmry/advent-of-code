use std::{
    collections::{VecDeque},
    fs::File,
    io::{BufRead, BufReader},
};

struct Machine {
    lights: u16,
    buttons: Vec<u16>,
    joltage_reqs: Vec<u16>,
}

fn main() -> Result<(), Box<dyn std::error::Error>> {
    let file = File::open("./input")?;
    let reader = BufReader::new(file);

    let machines = parse_input(reader);
    let result: usize = machines.iter().map(count_buttons).sum();

    println!("Result part 1: {result}");
    assert_eq!(475, result);

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

          for button in &machine.buttons {
              queue.push_back((state ^ button, depth + 1));
          }
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

        let buttons: Vec<u16> = line2
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
        Ok(())
    }
}
