use std::{
    fs::File,
    io::{BufRead, BufReader},
};

#[derive(Debug, Clone, Copy, PartialEq)]
struct Point {
    x: usize,
    y: usize,
}

#[derive(Debug)]
struct Rectangle {
    c1: Point,
    c2: Point,
    distance: f64,
}

fn main() -> Result<(), Box<dyn std::error::Error>> {
    let file = File::open("./input")?;
    let reader = BufReader::new(file);

    let tiles = parse_input(reader);

    let rectangles = list_rectangles(tiles);

    let br = rectangles.first().unwrap();
    let area = (1 + br.c2.x.abs_diff(br.c1.x)) * (1 + br.c2.y.abs_diff(br.c1.y));
    assert_eq!(4758598740, area);
    println!("Result: {:#?}", area);

    Ok(())
}

fn list_rectangles(tiles: Vec<Point>) -> Vec<Rectangle> {
    let mut rectangles: Vec<Rectangle> = Vec::new();
    for i in 0..tiles.len() - 1 {
        for j in i + 1..tiles.len() {
            let r = Rectangle {
                c1: tiles[i],
                c2: tiles[j],
                distance: (tiles[j].x.abs_diff(tiles[i].x).pow(2) as f64
                    + tiles[j].y.abs_diff(tiles[i].y).pow(2) as f64)
                    .sqrt(),
            };

            rectangles.push(r);
        }
    }

    // We don't need to order, just take the max. Let's see if it's needed in part 2
    rectangles.sort_by(|a, b| b.distance.total_cmp(&a.distance));
    rectangles
}

fn parse_input<R: BufRead>(reader: R) -> Vec<Point> {
    reader
        .lines()
        .map(|l| {
            let line = l.unwrap();
            let mut p = line.split_terminator(',');
            Point {
                x: p.next().unwrap().parse::<usize>().unwrap(),
                y: p.next().unwrap().parse::<usize>().unwrap(),
            }
        })
        .collect()
}

#[cfg(test)]
mod tests {
    use std::io::Cursor;

    use super::*;

    #[test]
    fn default() {
        let input = "7,1
11,1
11,7
9,7
9,5
2,5
2,3
7,3";

        let reader = Cursor::new(input);

        let tiles = parse_input(reader);

        let rectangles = list_rectangles(tiles);

        let br = rectangles.first().unwrap();
        let area = (1 + br.c2.x.abs_diff(br.c1.x)) * (1 + br.c2.y.abs_diff(br.c1.y));
        println!("Result: {:#?}", area);

        assert_eq!(50, area);
    }
}
