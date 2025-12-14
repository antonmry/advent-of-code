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

    // dbg! {&manifold};
    let result1 = count_splits(&mut manifold);
    println! {"Result part 1: {}", result1};
    assert!(result1 > 952);
    Ok(())
}

fn count_splits(manifold: &mut Vec<Vec<char>>) -> usize {
    let mut result = 0;
    for (y, row) in manifold.clone().iter().enumerate() {
        //let row = manifold[y].clone();
        // for x in 0..row.len() {
        for (x, cell) in row.iter().enumerate() {
            match cell {
                'S' => {
                    manifold[y + 1][x] = '|';
                }
                '^' => {
                    if manifold[y - 1][x] == '|' {
                        result += 1;
                        if let Some(left) = manifold[y + 1].get_mut(x - 1) {
                            *left = '|';
                        }
                        if let Some(right) = manifold[y + 1].get_mut(x + 1) {
                            *right = '|';
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

#[allow(dead_code)]
fn print_grid(grid: &[Vec<char>]) {
    for row in grid {
        println!("{}", row.iter().collect::<String>());
    }
}

#[cfg(test)]
mod tests {
    use std::io::{BufRead, Cursor};

    use crate::count_splits;

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
        Ok(())
    }
}
