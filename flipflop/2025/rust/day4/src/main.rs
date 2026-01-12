use std::fs;

fn main() {
    let input = fs::read_to_string("./input").unwrap();
    let result = part1(&input);
    assert_eq!(7069, result);
    let result = part2(&input);
    assert_eq!(4853, result);
    let result = part3(&input);
    assert_eq!(2067, result);
}

fn part1(input: &str) -> usize {
    parse_points(input)
        .fold(((0, 0), 0), |(pos, counter), x| {
            (x, counter + pos.0.abs_diff(x.0) + pos.1.abs_diff(x.1))
        })
        .1
}

fn part2(input: &str) -> usize {
    parse_points(input)
        .fold(((0, 0), 0), |(pos, counter), x| {
            (x, counter + part2_distance(x, pos))
        })
        .1
}

fn part2_distance(o: (usize, usize), i: (usize, usize)) -> usize {
    let x = o.0.abs_diff(i.0);
    let y = o.1.abs_diff(i.1);
    x.max(y)
}

fn part3(input: &str) -> usize {
    let mut trash: Vec<_> = parse_points(input).collect();

    trash.sort_by_key(|t| t.0 + t.1);

    trash
        .iter()
        .fold(((0, 0), 0), |(pos, counter), x| {
            (*x, counter + part2_distance(*x, pos))
        })
        .1
}

fn parse_points(input: &str) -> impl Iterator<Item = (usize, usize)> + '_ {
    input.lines().map(|m| {
        let p = m.split_once(',').unwrap();
        (p.0.parse::<usize>().unwrap(), p.1.parse::<usize>().unwrap())
    })
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

    #[test]
    fn part3test() {
        let input = "3,3
9,9
6,6";

        let result = part3(input);
        assert_eq!(9, result);
    }
}
