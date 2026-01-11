use std::{collections::HashMap, fs};

fn main() -> Result<(), Box<dyn std::error::Error>> {
    let input = fs::read_to_string("./input")?;
    let result = part1(&input);
    assert_eq!("71,50,92", result);
    Ok(())
}

fn part1(input: &str) -> &str {
    input
        .lines()
        .fold(HashMap::new(), |mut counts, line| {
            *counts.entry(line).or_insert(0) += 1;
            counts
        })
        .into_iter()
        .max_by_key(|(_, count)| *count)
        .map(|(line, _)| line)
        .unwrap_or("")
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn first() {
        let input = "10,20,30
20,10,30
30,20,10
10,50,10
50,10,50
10,20,30";

        let result = part1(input);
        assert_eq!("10,20,30", result);
    }
}
