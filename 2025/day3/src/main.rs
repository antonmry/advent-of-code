use std::fs::File;
use std::io::{self, BufRead};

fn main() -> Result<(), Box<dyn std::error::Error>> {
    let file = File::open("./input")?;
    let reader = io::BufReader::new(file);
    let result: usize = reader.lines().map(|l| largest_joltage(&l.unwrap())).sum();
    println!("Result {result}");
    Ok(())
}

pub fn largest_joltage(reading: &str) -> usize {
    let (first, _) = reading.split_at(reading.len() - 1);

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

    let (_, second) = reading.split_at(idx + 1);
    let val2 = second
        .chars()
        .map(|ch| ch.to_digit(10).unwrap() as usize)
        .max()
        .unwrap();

    10 * val + val2
}

#[cfg(test)]
mod tests {
    use super::largest_joltage;

    #[test]
    fn computes_expected_joltages_from_readings() {
        assert_eq!(largest_joltage("987654321111111"), 98);
        assert_eq!(largest_joltage("811111111111119"), 89);
        assert_eq!(largest_joltage("234234234234278"), 78);
        assert_eq!(largest_joltage("818181911112111"), 92);
    }
}
