use std::{
    collections::HashMap,
    fs::File,
    io::{BufRead, BufReader},
};

use pathfinding::prelude::count_paths;

fn main() -> Result<(), Box<dyn std::error::Error>> {
    let file = File::open("./input")?;
    let reader = BufReader::new(file);
    let graph = parse_input(reader);
    let successors =
        |node: &String| -> Vec<String> { graph.get(node).cloned().unwrap_or_default() };
    let result = count_paths("you".to_string(), successors, |c| c == "out");
    assert_eq!(696, result);
    println!("Result part 1: {result}");
    Ok(())
}

fn parse_input<R: BufRead>(reader: R) -> HashMap<String, Vec<String>> {
    reader
        .lines()
        .map_while(|line| line.ok())
        .map(|line| {
            let mut split = line.split_whitespace();
            let key = split.next().unwrap().trim_end_matches(':').to_string();
            let values = split.map(|s| s.to_string()).collect();
            (key, values)
        })
        .collect()
}

#[cfg(test)]
mod tests {
    use std::io::Cursor;

    use super::*;

    #[test]
    fn default() {
        let input = "aaa: you hhh
you: bbb ccc
bbb: ddd eee
ccc: ddd eee fff
ddd: ggg
eee: out
fff: out
ggg: out
hhh: ccc fff iii
iii: out";

        let reader = Cursor::new(input);
        let graph = parse_input(reader);
        let successors =
            |node: &String| -> Vec<String> { graph.get(node).cloned().unwrap_or_default() };
        let result = count_paths("you".to_string(), successors, |c| c == "out");
        assert_eq!(5, result);
    }
}
