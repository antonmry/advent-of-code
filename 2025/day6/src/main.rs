use std::{
    fs::File,
    io::{self, BufRead},
};

#[derive(Debug, Default, Clone)]
struct MathWork {
    numbers: Vec<Vec<usize>>,
    operations: Vec<char>,
}

fn main() -> Result<(), Box<dyn std::error::Error>> {
    let file = File::open("./input")?;
    let reader = io::BufReader::new(file);
    let lines: Vec<String> = reader.lines().collect::<Result<_, _>>()?;

    let homework = parse_homework(&lines).expect("input isn't valid");

    println!("Result part 1: {}", part_1(&homework));
    println!("Result part 2: {}", part_2(&lines));

    Ok(())
}

fn get_homework<R: BufRead>(reader: &mut R) -> Result<MathWork, Box<dyn std::error::Error>> {
    let lines: Vec<String> = reader.lines().collect::<Result<_, _>>()?;
    parse_homework(&lines)
}

fn parse_homework(lines: &[String]) -> Result<MathWork, Box<dyn std::error::Error>> {
    let mut homework = MathWork::default();
    for line in lines {
        if line.contains('+') || line.contains('*') {
            homework.operations = line.chars().filter(|&c| c == '+' || c == '*').collect();
        } else {
            let row_numbers: Vec<usize> = line
                .split_ascii_whitespace()
                .map(|n| n.parse::<usize>().unwrap())
                .collect();

            homework.numbers.push(row_numbers);
        }
    }

    Ok(homework)
}

fn part_1(homework: &MathWork) -> usize {
    homework
        .operations
        .iter()
        .enumerate()
        .map(|(idx, op)| match op {
            '+' => homework
                .numbers
                .iter()
                .filter_map(|row| row.get(idx))
                .copied()
                .sum::<usize>(),
            '*' => homework
                .numbers
                .iter()
                .filter_map(|row| row.get(idx))
                .copied()
                .product::<usize>(),
            _ => panic!("op not supported"),
        })
        .sum()
}

fn part_2(lines: &[String]) -> usize {
    let rows: Vec<Vec<char>> = lines.iter().map(|l| l.chars().collect()).collect();
    let max_len = rows.iter().map(|r| r.len()).max().unwrap_or(0);

    let mut result = 0;
    let mut temp = 0;
    let mut op = ' ';

    for col in 0..max_len {
        let mut digits = String::new();
        let mut found_op = None;
        let mut saw_non_ws = false;

        for c in rows.iter().filter_map(|r| r.get(col)).copied() {
            if c.is_whitespace() {
                continue;
            }
            saw_non_ws = true;
            if c == '+' || c == '*' {
                found_op = Some(c);
                break;
            }
            digits.push(c);
        }

        if !saw_non_ws {
            result += temp;
            temp = 0;
            continue;
        }

        if let Some(new_op) = found_op {
            op = new_op;
            temp = digits.trim().parse::<usize>().unwrap();
            continue;
        }

        let value = digits.parse::<usize>().unwrap();

        match op {
            '+' => temp += value,
            '*' => temp *= value,
            _ => panic!("op not supported"),
        }
    }

    result + temp
}

#[cfg(test)]
mod tests {
    use std::io::Cursor;

    use crate::{get_homework, part_1, part_2};

    #[test]
    fn default_example() {
        let input = "123 328  51 64 
 45 64  387 23 
  6 98  215 314
*   +   *   + ";

        let mut reader = Cursor::new(input);
        let homework = get_homework(&mut reader).expect("input isn't valid");
        assert_eq!(4277556_usize, part_1(&homework));
    }

    #[test]
    fn default_example_part_2() {
        let lines: Vec<String> = "123 328  51 64 
 45 64  387 23 
  6 98  215 314
*   +   *   + "
            .lines()
            .map(|l| l.to_string())
            .collect();

        assert_eq!(3263827_usize, part_2(&lines));
    }
}
