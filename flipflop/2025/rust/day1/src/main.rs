use std::{
    fs::File,
    io::{BufRead, BufReader},
};

fn main() -> Result<(), Box<dyn std::error::Error>> {
    let file = File::open("./input")?;
    let reader = BufReader::new(file);

    let halves: Vec<String> = reader.lines().map_while(|l| l.ok()).collect();

    let result: usize = halves.iter().map(|l| l.len() / 2).sum();
    assert_eq!(result, 10233);

    let result: usize = halves
        .iter()
        .map(|l| l.len() / 2)
        .filter(|b| b.rem_euclid(2) == 0)
        .sum();
    assert_eq!(result, 5226);

    let result: usize = halves
        .iter()
        .filter(|b| !b.contains("ne"))
        .map(|l| l.len() / 2)
        .sum();
    assert_eq!(result, 3060);

    Ok(())
}

#[cfg(test)]
mod tests {
    use std::io::{BufRead, Cursor};

    // use super::*;

    #[test]
    fn default() {
        let input = "banana
banenanana
bananana
bananananana
bananananana";

        let reader = Cursor::new(input);

        let halves: Vec<String> = reader.lines().map_while(|l| l.ok()).collect();

        let result: usize = halves.iter().map(|l| l.len() / 2).sum();
        assert_eq!(result, 24);

        let result: usize = halves
            .iter()
            .map(|l| l.len() / 2)
            .filter(|b| b.rem_euclid(2) == 0)
            .sum();
        assert_eq!(result, 16);

        let result: usize = halves
            .iter()
            .filter(|b| !b.contains("ne"))
            .map(|l| l.len() / 2)
            .sum();
        assert_eq!(result, 19);
    }
}
