use std::fs::File;
use std::io::{self, BufRead};

fn main() -> Result<(), Box<dyn std::error::Error>> {
    let file = File::open("./input")?;
    let reader = io::BufReader::new(file);
    let result: usize = reader.lines().map(|l| largest_joltage2(&l.unwrap())).sum();
    println!("Result {result}");
    Ok(())
}

#[allow(dead_code)]
pub fn largest_joltage(reading: &str) -> usize {
    let first = reading.get(0..reading.len() - 1).unwrap();

    let (idx, val) = first
        .chars()
        .enumerate()
        .map(|(idx, ch)| (idx, ch.to_digit(10).unwrap() as usize))
        .fold(None, |acc, (idx, val)| match acc {
            None => Some((idx, val)),
            Some((_, acc_v)) if val > acc_v => Some((idx, val)),
            _ => acc, // tie or smaller → keep the first max
        })
        .unwrap();

    let second = reading.get(idx + 1..reading.len()).unwrap();
    let val2 = second
        .chars()
        .map(|ch| ch.to_digit(10).unwrap() as usize)
        .max()
        .unwrap();

    10 * val + val2
}

pub fn largest_joltage2(reading: &str) -> usize {
    let mut idx: usize = 0;
    let mut result: usize = 0;
    for pos in 1..=12 {
        let first = reading.get(idx..reading.len() - 12 + pos).unwrap();

        let (i, val) = first
            .chars()
            .enumerate()
            .map(|(idx, ch)| (idx, ch.to_digit(10).unwrap() as usize))
            .fold(None, |acc, (idx, val)| match acc {
                None => Some((idx, val)),
                Some((_, acc_v)) if val > acc_v => Some((idx, val)),
                _ => acc, // tie or smaller → keep the first max
            })
            .unwrap();

        result = result * 10 + val;
        idx = idx + i + 1;
    }
    result
}

#[cfg(test)]
mod tests {
    use super::largest_joltage;
    use super::largest_joltage2;

    #[test]
    fn computes_expected_joltages_from_readings() {
        assert_eq!(largest_joltage("987654321111111"), 98);
        assert_eq!(largest_joltage("811111111111119"), 89);
        assert_eq!(largest_joltage("234234234234278"), 78);
        assert_eq!(largest_joltage("818181911112111"), 92);
    }

    #[test]
    fn computes_joltage_when_keeping_most_batteries() {
        assert_eq!(largest_joltage2("987654321111111"), 987654321111);
        assert_eq!(largest_joltage2("811111111111119"), 811111111119);
        assert_eq!(largest_joltage2("234234234234278"), 434234234278);
        assert_eq!(largest_joltage2("818181911112111"), 888911112111);
    }
}
