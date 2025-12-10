use std::fmt;
use std::fs::File;
use std::io::{self, BufRead};

#[derive(Debug, Default)]
pub struct Grid {
    rows: Vec<Vec<u8>>,
    cols: usize,
}

fn main() -> Result<(), Box<dyn std::error::Error>> {
    let file = File::open("./input")?;
    let reader = io::BufReader::new(file);
    let mut grid = Grid::default();
    reader
        .lines()
        .map(|l| grid.add_row(&l.unwrap()))
        .for_each(|l| l.expect("All lines should be valid"));

    println! {"Result: {}", sum_ats(grid)}
    Ok(())
}

fn sum_ats(grid: Grid) -> usize {
    let mut result: usize = 0;
    for r in 0..grid.rows.len() {
        for c in 0..grid.rows[r].len() {
            if grid.get(r, c) == Some(0) {
                continue; // Early optimization, not needed
            }
            let mut rec = 0;
            for i in -1isize..=1 {
                for j in -1isize..=1 {
                    if r as isize + i >= 0 && c as isize + j >= 0 && (i != 0 || j != 0) {
                        rec += grid
                            .get((r as isize + i) as usize, (c as isize + j) as usize)
                            .unwrap_or(0) as usize;
                    }
                }
            }
            if rec < 4 {
                result += grid.get(r, c).unwrap_or(0) as usize;
            }
        }
    }
    result
}

impl Grid {
    pub fn get(&self, r: usize, c: usize) -> Option<u8> {
        self.rows.get(r).and_then(|row| row.get(c)).copied()
    }

    pub fn add_row(&mut self, line: &str) -> Result<(), String> {
        if line.is_empty() {
            return Err("empty row".into());
        }
        let mut row = Vec::with_capacity(line.len());
        for (cn, b) in line.bytes().enumerate() {
            row.push(match b {
                b'.' => 0,
                b'@' => 1,
                _ => return Err(format!("invalid char at col {}", cn + 1)),
            });
        }

        if self.cols == 0 {
            self.cols = row.len();
        } else if row.len() != self.cols {
            return Err(format!(
                "inconsistent row length: expected {}, got {}",
                self.cols,
                row.len()
            ));
        }

        self.rows.push(row);
        Ok(())
    }
}

impl fmt::Display for Grid {
    fn fmt(&self, f: &mut fmt::Formatter<'_>) -> fmt::Result {
        for row in &self.rows {
            for &cell in row {
                write!(f, "{}", if cell == 1 { '@' } else { '.' })?;
            }
            writeln!(f)?;
        }
        Ok(())
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    const SAMPLE: [&str; 10] = [
        "..@@.@@@@.",
        "@@@.@.@.@@",
        "@@@@@.@.@@",
        "@.@@@@..@.",
        "@@.@@@@.@@",
        ".@@@@@@@.@",
        ".@.@.@.@@@",
        "@.@@@.@@@@",
        ".@@@@@@@@.",
        "@.@.@@@.@.",
    ];

    #[test]
    fn get_sum_sample_grid() {
        let mut grid = Grid::default();
        for line in SAMPLE {
            grid.add_row(line).expect("valid sample row");
        }
        println!("{}", grid);

        assert_eq!(13, sum_ats(grid));
    }

    #[test]
    fn parses_sample_grid() {
        let mut grid = Grid::default();
        for line in SAMPLE {
            grid.add_row(line).expect("valid sample row");
        }

        assert_eq!(grid.cols, 10);
        assert_eq!(grid.get(0, 0), Some(0));
        assert_eq!(grid.get(0, 2), Some(1));
        assert_eq!(grid.get(9, 9), Some(0));
        assert_eq!(grid.get(9, 0), Some(1));
        assert_eq!(grid.get(10, 0), None);
    }
}
