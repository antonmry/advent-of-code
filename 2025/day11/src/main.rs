use std::{
    collections::HashMap,
    fs::File,
    io::{BufRead, BufReader},
};

use pathfinding::prelude::count_paths;

#[derive(Debug, Clone, Eq, PartialEq, Hash)]
struct Node {
    name: String,
    dac: bool,
    fft: bool,
}

impl Node {
    fn new(name: impl Into<String>) -> Self {
        Self {
            name: name.into(),
            dac: false,
            fft: false,
        }
    }
}

fn main() -> Result<(), Box<dyn std::error::Error>> {
    let file = File::open("./input")?;
    let reader = BufReader::new(file);
    let graph = parse_input(reader);

    let result = part1(&graph);
    assert_eq!(696, result);
    println!("Result part 1: {result}");

    let result = part2(&graph);
    println!("Result part 2: {}", result);
    assert_eq!(473741288064360, result);

    Ok(())
}

fn part1(graph: &HashMap<String, Vec<String>>) -> usize {
    let successors =
        |node: &String| -> Vec<String> { graph.get(node).cloned().unwrap_or_default() };

    count_paths("you".to_string(), successors, |c| c == "out")
}

fn part2(graph: &HashMap<String, Vec<String>>) -> usize {
    let successors = |node: &Node| -> Vec<Node> {
        graph
            .get(&node.name)
            .cloned()
            .unwrap_or_default()
            .into_iter()
            .map(|f| Node {
                name: f,
                dac: node.dac || node.name == "dac",
                fft: node.fft || node.name == "fft",
            })
            .collect()
    };

    let success = |c: &Node| c.name == "out" && c.fft && c.dac;

    let start = Node::new("svr");

    count_paths(start, successors, success)
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
        assert_eq!(5, part1(&graph));
    }

    #[test]
    fn default2() {
        let input = "svr: aaa bbb
aaa: fft
fft: ccc
bbb: tty
tty: ccc
ccc: ddd eee
ddd: hub
hub: fff
eee: dac
dac: fff
fff: ggg hhh
ggg: out
hhh: out";

        let reader = Cursor::new(input);
        let graph = parse_input(reader);
        assert_eq!(2, part2(&graph));
    }
}
