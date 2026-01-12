use std::fs;

struct Steps {
    position: (usize, usize),
    counter: usize,
}

fn main() {
    let input = fs::read_to_string("./input").unwrap();
    let result = part1(&input);
    assert_eq!(7069, result);
}

fn part1(input: &str) -> usize {
    input
        .lines()
        .map(|m| {
            let p = m.split_once(',').unwrap();
            (p.0.parse::<usize>().unwrap(), p.1.parse::<usize>().unwrap())
        })
        .fold(
            Steps {
                position: (0, 0),
                counter: 0,
            },
            |acc, x| Steps {
                position: (x.0, x.1),
                counter: acc.counter + acc.position.0.abs_diff(x.0) + acc.position.1.abs_diff(x.1),
            },
        )
        .counter
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn part1test() {
        let input = "3,3
9,9
6,6";

        let result = part1(input);
        assert_eq!(24, result);
    }
}
