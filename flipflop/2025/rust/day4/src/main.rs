use std::fs;

struct Steps {
    position: (usize, usize),
    counter: usize,
}

fn main() {
    let input = fs::read_to_string("./input").unwrap();
    let result = part1(&input);
    assert_eq!(7069, result);
    let result = part2(&input);
    assert_eq!(4853, result);
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

fn part2(input: &str) -> usize {
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
                counter: acc.counter + part2_distance(x, acc.position),
            },
        )
        .counter
}

fn part2_distance(o: (usize, usize), i: (usize, usize)) -> usize {
    let x = o.0.abs_diff(i.0);
    let y = o.1.abs_diff(i.1);
    x.min(y) + x.abs_diff(y)
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

    #[test]
    fn distance2() {
        assert_eq! {part2_distance((3,3), (9,9)), 6};
        assert_eq! {part2_distance((9,9), (6,6)), 3};
        assert_eq! {part2_distance((3,2), (6,6)), 4};
        assert_eq! {part2_distance((0,0), (0,6)), 6};
    }
}
