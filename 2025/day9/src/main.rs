use std::{
    collections::{HashMap, HashSet},
    fs::File,
    io::{BufRead, BufReader},
    time::Instant,
};

#[derive(Debug, Clone, Copy, PartialEq, Eq, Hash)]
struct Point {
    x: usize,
    y: usize,
}

#[derive(Debug, Clone)]
struct Rectangle {
    c1: Point,
    c2: Point,
    area: usize,
}

fn main() -> Result<(), Box<dyn std::error::Error>> {
    let file = File::open("./input")?;
    let reader = BufReader::new(file);
    let tiles = parse_input(reader);

    let rectangles = list_rectangles(&tiles);

    let br = rectangles.first().unwrap();
    assert_eq!(4758598740, br.area);
    println!("Result part 1: {:#?}", br.area);

    let br = part2(&rectangles, &tiles).unwrap();
    assert_eq!(1474699155, br.area);
    println!("Result part 2: {:#?}", br.area);
    Ok(())
}

fn part2(
    rectangles: &[Rectangle],
    red_tiles: &[Point],
) -> Result<Rectangle, Box<dyn std::error::Error>> {
    let start = Instant::now();

    let mut green_tiles: HashSet<Point> = HashSet::new();
    find_green_tiles(red_tiles, &mut green_tiles);
    println!("Find: {:#?}", start.elapsed());

    // Convert red/green tiles into ranges by row (more efficient)
    let mut rows: HashMap<usize, (usize, usize)> = HashMap::new();
    for p in red_tiles.iter().chain(green_tiles.iter()) {
        rows.entry(p.y)
            .and_modify(|(xmin, xmax)| {
                *xmin = (*xmin).min(p.x);
                *xmax = (*xmax).max(p.x);
            })
            .or_insert((p.x, p.x));
    }
    println!("Ranges: {:#?}", start.elapsed());

    // Find the biggest valid rectangle
    for r in rectangles {
        let (x1, x2) = (r.c1.x.min(r.c2.x), r.c1.x.max(r.c2.x));
        let (y1, y2) = (r.c1.y.min(r.c2.y), r.c1.y.max(r.c2.y));

        let is_filled = (y1..=y2).all(|y| {
            rows.get(&y)
                .is_some_and(|(xmin, xmax)| *xmin <= x1 && *xmax >= x2)
        });

        if is_filled {
            println!("Found: {:#?}", start.elapsed());
            return Ok(r.clone());
        }
    }

    Err("Rectangles not found".into())
}

fn find_green_tiles(red_tiles: &[Point], green_tiles: &mut HashSet<Point>) {
    // Find green tiles
    for tile in red_tiles {
        // Points horizontal
        if let Some(right) = red_tiles
            .iter()
            .filter(|t| t.x > tile.x && t.y == tile.y)
            .min_by_key(|t| t.x)
        {
            for i in tile.x..=right.x {
                green_tiles.insert(Point { x: i, y: tile.y });
            }
        }

        // Points vertical
        if let Some(up) = red_tiles
            .iter()
            .filter(|t| t.y < tile.y && t.x == tile.x)
            .max_by_key(|t| t.y)
        {
            for i in up.y..=tile.y {
                green_tiles.insert(Point { x: tile.x, y: i });
            }
        }
    }
}

fn list_rectangles(tiles: &[Point]) -> Vec<Rectangle> {
    let mut rectangles: Vec<Rectangle> = Vec::new();
    for i in 0..tiles.len() - 1 {
        for j in i + 1..tiles.len() {
            let r = Rectangle {
                c1: tiles[i],
                c2: tiles[j],
                area: (1 + tiles[j].x.abs_diff(tiles[i].x)) * (1 + tiles[j].y.abs_diff(tiles[i].y)),
            };

            rectangles.push(r);
        }
    }

    rectangles.sort_by(|a, b| b.area.cmp(&a.area));
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

        let rectangles = list_rectangles(&tiles);

        let br = rectangles.first().unwrap();
        let area = (1 + br.c2.x.abs_diff(br.c1.x)) * (1 + br.c2.y.abs_diff(br.c1.y));
        println!("Result part 1: {:#?}", area);
        assert_eq!(50, area);

        let br = part2(&rectangles, &tiles).unwrap();
        println!("Result part 2: {:#?}", br.area);
        assert_eq!(24_usize, br.area);
    }
}
