use std::convert::Infallible;
use std::fs;
use std::str::FromStr;

use pathfinding::prelude::count_paths;
// use pathfinding::prelude::dijkstra;

#[derive(Clone, Debug, Eq, Hash, Ord, PartialEq, PartialOrd, Copy)]
struct Pos(i32, i32);

impl Pos {
    fn successors(&self) -> Vec<(Pos, usize)> {
        let &Pos(x, y) = self;
        vec![Pos(x, y + 1), Pos(x + 1, y)]
            .into_iter()
            .map(|p| (p, 1))
            .collect()
    }
}

impl FromStr for Pos {
    type Err = Infallible;

    fn from_str(s: &str) -> Result<Self, Self::Err> {
        let (x, y) = s.split_once(' ').unwrap();
        Ok(Pos(
            x.parse::<i32>().unwrap() - 1,
            y.parse::<i32>().unwrap() - 1,
        ))
    }
}

// #[derive(Clone, Debug, Eq, Hash, Ord, PartialEq, PartialOrd)]
// struct PosAcc(Pos, usize);

fn main() {
    let input = fs::read_to_string("./input").unwrap();

    let result: usize = input
        .lines()
        .map(|l| l.parse::<Pos>().unwrap())
        .map(count_shortest_paths)
        .sum();

    assert_eq!(176, result);
}

// fn shortest_path(goal: &Pos) -> Option<(Vec<Pos>, usize)> {
//     dijkstra(&Pos(0, 0), |p| p.successors(), |p| p == goal)
// }

fn count_shortest_paths(goal: Pos) -> usize {
    count_paths(
        Pos(0, 0),
        |p| {
            p.successors()
                // TODO: depending on the other two parts, move this into Impl
                .into_iter()
                .filter_map(|(pos, _)| (pos.0 <= goal.0 && pos.1 <= goal.1).then_some(pos))
                .collect::<Vec<_>>()
        },
        |p| *p == goal,
    )
}

// fn count_shortest_paths(goal: Pos) -> usize {
//     let s_p = shortest_path(&goal).unwrap().1;
//
//     count_paths(
//         PosAcc(Pos(0, 0), 0),
//         |&PosAcc(Pos(x, y), acc)| {
//             [
//                 PosAcc(Pos(x + 1, y), acc + 1),
//                 PosAcc(Pos(x, y + 1), acc + 1),
//             ]
//             .into_iter()
//             .filter(|&PosAcc(Pos(x, y), acc)| x <= goal.0 && y <= goal.1 && acc <= s_p)
//         },
//         |c| c.0 == goal,
//     )
// }

#[cfg(test)]
mod tests {

    use super::*;

    #[test]
    fn part1_test() {
        let goal = Pos(2 - 1, 2 - 1);
        // let s_ps = shortest_path(&goal).unwrap().1 + 1;
        // assert_eq!(3, s_ps);
        let n = count_shortest_paths(goal);
        assert_eq!(2, n);

        let goal = Pos(3 - 1, 3 - 1);
        // let s_ps = shortest_path(&goal).unwrap().1 + 1;
        // assert_eq!(5, s_ps);
        let n = count_shortest_paths(goal);
        assert_eq!(6, n);

        let goal = Pos(2 - 1, 3 - 1);
        // let s_ps = shortest_path(&goal).unwrap().1 + 1;
        // assert_eq!(4, s_ps);
        let n = count_shortest_paths(goal);
        assert_eq!(3, n);
    }
}
