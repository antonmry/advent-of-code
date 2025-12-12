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
    let mut reader = io::BufReader::new(file);

    let homework = get_homework(&mut reader).expect("input isn't valid");

    println!("Result part 1: {}", part_1(homework));

    Ok(())
}

fn get_homework<R: BufRead>(reader: &mut R) -> Result<MathWork, Box<dyn std::error::Error>> {
    let mut homework = MathWork::default();
    for line in reader.by_ref().lines() {
        let line = line?;
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

fn part_1(homework: MathWork) -> usize {
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

#[cfg(test)]
mod tests {
    use std::io::Cursor;

    use crate::{get_homework, part_1};

    #[test]
    fn default_example() {
        let input = "123 328  51 64 
 45 64  387 23 
  6 98  215 314
*   +   *   + ";

        let mut reader = Cursor::new(input);
        let homework = get_homework(&mut reader).expect("input isn't valid");
        assert_eq!(4277556_usize, part_1(homework));
    }
}
