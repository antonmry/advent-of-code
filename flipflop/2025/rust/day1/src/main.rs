use std::{
    fs::File,
    io::{BufRead, BufReader},
};

fn main() -> Result<(), Box<dyn std::error::Error>> {
    let file = File::open("./input")?;
    let reader = BufReader::new(file);

    let halves: Vec<usize> = reader
        .lines()
        .map_while(|l| l.ok())
        .map(|l| l.len() / 2)
        .collect();

    let result: usize = halves.iter().sum();
    assert_eq!(result, 10233);

    let result: usize = halves.iter().filter(|b| b.rem_euclid(2) == 0).sum();
    assert_eq!(result, 5226);

    Ok(())
}

#[cfg(test)]
mod tests {
    use std::io::{BufRead, Cursor};

    // use super::*;

    #[test]
    fn day1() {
        let input = "banana
banenanana
bananana
bananananana
bananananana";

        let reader = Cursor::new(input);

        let result: usize = reader
            .lines()
            .map_while(|l| l.ok())
            .map(|l| l.len() / 2)
            .sum();

        assert_eq!(result, 24);
    }

    #[test]
    fn day2() {
        let input = "banana
banenanana
bananana
bananananana
bananananana";

        let reader = Cursor::new(input);

        let result: usize = reader
            .lines()
            .map_while(|l| l.ok())
            .map(|l| l.len() / 2)
            .filter(|b| b.rem_euclid(2) == 0)
            .sum();

        assert_eq!(result, 16);
    }
}
