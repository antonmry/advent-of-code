use std::{
    fs::File,
    io::{BufRead, BufReader},
};

fn main() -> Result<(), Box<dyn std::error::Error>> {
    let file = File::open("./input")?;
    let reader = BufReader::new(file);

    let mut manifold: Vec<Vec<char>> = vec![];
    for line in reader.lines() {
        manifold.push(line?.chars().collect());
    }

    let result1 = count_splits(&mut manifold.clone());
    assert_eq!(1579, result1);
    println! {"Result part 1: {}", result1};

    let result2 = count_timelines(&manifold);
    assert_eq!(13418215871354, result2);
    println! {"Result part 2: {}", result2};

    Ok(())
}

fn count_splits(manifold: &mut [Vec<char>]) -> usize {
    let mut result = 0;
    for y in 0..manifold.len() {
        for x in 0..manifold[y].len() {
            match manifold[y][x] {
                'S' => {
                    manifold[y + 1][x] = '|';
                }
                '^' => {
                    if manifold[y - 1][x] == '|' {
                        result += 1;
                        if x > 0 {
                            manifold[y + 1][x - 1] = '|';
                        }
                        if x + 1 < manifold[y + 1].len() {
                            manifold[y + 1][x + 1] = '|';
                        }
                    }
                }
                '.' => {
                    if y > 0 && manifold[y - 1][x] == '|' {
                        manifold[y][x] = '|'
                    }
                }
                '|' => continue,
                _ => panic!("Character not allowed in input"),
            }
        }
    }
    result
}

fn count_timelines(manifold: &[Vec<char>]) -> usize {
    let mut result: Vec<usize> = vec![0; manifold[0].len()];

    for m in manifold {
        for x in 0..m.len() {
            match m[x] {
                'S' => {
                    result[x] = 1;
                }
                '^' => {
                    if result[x] > 0 {
                        result[x - 1] += result[x];
                        result[x + 1] += result[x];
                        result[x] = 0;
                    }
                }
                '.' => continue,
                '|' => continue,
                _ => panic!("Character not allowed in input"),
            }
        }
    }
    result.iter().sum()
}

#[allow(dead_code)]
fn print_grid(grid: &[Vec<char>]) {
    for row in grid {
        println!("{}", row.iter().collect::<String>());
    }
}

#[cfg(test)]
mod tests {
    use std::io::{BufRead, Cursor};

    use crate::{count_splits, count_timelines};

    #[test]
    fn default_example() -> Result<(), Box<dyn std::error::Error>> {
        let input = ".......S.......
.......|.......
......|^|......
......|.|......
.....|^|^|.....
.....|.|.|.....
....|^|^|^|....
....|.|.|.|....
...|^|^|||^|...
...|.|.|||.|...
..|^|^|||^|^|..
..|.|.|||.|.|..
.|^|||^||.||^|.
.|.|||.||.||.|.
|^|^|^|^|^|||^|
|.|.|.|.|.|||.|";

        let mut manifold: Vec<Vec<char>> = vec![];

        let reader = Cursor::new(input);
        for line in reader.lines() {
            manifold.push(line?.chars().collect());
        }

        let result = count_splits(&mut manifold);
        assert_eq!(21_usize, result);

        let result = count_timelines(&manifold);
        assert_eq!(40_usize, result);

        Ok(())
    }
}
