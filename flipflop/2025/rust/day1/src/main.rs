use std::{fs::File, io::{BufRead, BufReader}};

fn main() -> Result<(), Box<dyn std::error::Error>> {
    let file = File::open("./input")?;
    let reader = BufReader::new(file);

    let result: usize = reader
        .lines()
        .map_while(|l| l.ok())
        .map(|l| l.len() / 2)
        .sum();

    assert_eq!(result, 24);

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
}
