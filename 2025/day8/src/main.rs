use std::{
    error::Error,
    fs::File,
    io::{BufRead, BufReader},
    time::Instant,
};

#[derive(Debug, Clone, Copy, PartialEq)]
struct Coordinates {
    x: usize,
    y: usize,
    z: usize,
}

#[derive(Debug)]
struct BoxesDistance {
    jb1: Coordinates,
    jb2: Coordinates,
    distance: usize,
}

fn main() -> Result<(), Box<dyn std::error::Error>> {
    let file = File::open("./input")?;
    let reader = BufReader::new(file);

    // Parsing input
    let junction_boxes = parse_input(reader)?;

    // List of junction boxes ordered by distance
    let ordered_junction_boxes = list_distance_circuits(&junction_boxes);

    let result = exercise1(&junction_boxes, &ordered_junction_boxes, 1000)?;
    assert_eq!(175500, result);
    println!("Exercise 1: {:#?}", result);

    let result = exercise2(&junction_boxes, &ordered_junction_boxes)?;
    assert_eq!(6934702555, result);
    println!("Exercise 2: {:#?}", result);

    Ok(())
}

fn exercise1(
    junction_boxes: &[Coordinates],
    ordered_junction_boxes: &[BoxesDistance],
    pairs: usize,
) -> Result<usize, Box<dyn Error + 'static>> {
    let start = Instant::now();
    let mut circuits: Vec<Vec<Coordinates>> = junction_boxes.iter().map(|j_b| vec![*j_b]).collect();

    for c_j_b in ordered_junction_boxes.iter().take(pairs) {
        // Find circuits containing each junction box
        let c1 = circuits.iter().position(|c| c.contains(&c_j_b.jb1));
        let c2 = circuits.iter().position(|c| c.contains(&c_j_b.jb2));

        if let (Some(c1), Some(c2)) = (c1, c2)
            && c1 != c2
        {
            let mut c2_vec = std::mem::take(&mut circuits[c2]);
            // It leaves the empty vec, not a problem for exercise 1
            circuits[c1].append(&mut c2_vec);
        }
    }

    circuits.sort_by_key(|v| std::cmp::Reverse(v.len()));

    let result: usize = circuits.iter().take(3).map(|c| c.len()).product();
    println!("Time for exercise 1: {:#?}", start.elapsed());
    Ok(result)
}

fn exercise2(
    junction_boxes: &[Coordinates],
    ordered_junction_boxes: &Vec<BoxesDistance>,
) -> Result<usize, Box<dyn Error + 'static>> {
    let start = Instant::now();
    let mut circuits: Vec<Vec<Coordinates>> = junction_boxes.iter().map(|j_b| vec![*j_b]).collect();
    let mut circuit_count = circuits.len();

    for c_j_b in ordered_junction_boxes {
        // Using a HashMap to track circuits would be faster (O(1) vs O(n))
        let c1 = circuits.iter().position(|c| c.contains(&c_j_b.jb1));
        let c2 = circuits.iter().position(|c| c.contains(&c_j_b.jb2));

        let (Some(c1), Some(c2)) = (c1, c2) else {
            continue;
        };

        if c1 == c2 {
            continue;
        }

        if circuit_count == 2 {
            println!("Time for exercise 2: {:#?}", start.elapsed());
            return Ok(c_j_b.jb1.x * c_j_b.jb2.x);
        }

        let mut c2_vec = std::mem::take(&mut circuits[c2]);
        circuits[c1].append(&mut c2_vec);
        circuit_count -= 1;
    }

    Err("No result found".into())
}

// Parse file into Vec of structs
fn parse_input<R: BufRead>(reader: R) -> Result<Vec<Coordinates>, Box<dyn Error + 'static>> {
    reader
        .lines()
        .map(|line| {
            let line = line?;
            let (x, rest) = line.split_once(',').expect("Input invalid");
            let (y, z) = rest.split_once(',').expect("Input invalid");
            Ok(Coordinates {
                x: x.parse()?,
                y: y.parse()?,
                z: z.parse()?,
            })
        })
        .collect::<Result<Vec<_>, Box<dyn Error>>>()
}

// Calculate distance between all points
fn list_distance_circuits(junction_boxes: &[Coordinates]) -> Vec<BoxesDistance> {
    let start = Instant::now();
    let mut closest_junction_boxes: Vec<BoxesDistance> = junction_boxes
        .iter()
        .enumerate()
        .flat_map(|(i, c1)| {
            junction_boxes.iter().skip(i + 1).map(move |c2| {
                let dx = c1.x.abs_diff(c2.x);
                let dy = c1.y.abs_diff(c2.y);
                let dz = c1.z.abs_diff(c2.z);
                BoxesDistance {
                    jb1: *c1,
                    jb2: *c2,
                    distance: dx * dx + dy * dy + dz * dz,
                }
            })
        })
        .collect();

    println!("Calculating distances: {:#?}", start.elapsed());
    let start = Instant::now();
    closest_junction_boxes.sort_by_key(|v| v.distance);
    println!("Ordering by distance: {:#?}", start.elapsed());
    closest_junction_boxes
}

#[cfg(test)]
mod tests {
    use std::io::Cursor;

    use crate::{exercise1, exercise2, list_distance_circuits, parse_input};

    #[test]
    fn default_example() -> Result<(), Box<dyn std::error::Error>> {
        let input = "162,817,812
57,618,57
906,360,560
592,479,940
352,342,300
466,668,158
542,29,236
431,825,988
739,650,466
52,470,668
216,146,977
819,987,18
117,168,530
805,96,715
346,949,466
970,615,88
941,993,340
862,61,35
984,92,344
425,690,689";

        let reader = Cursor::new(input);
        let junction_boxes = parse_input(reader)?;
        let ordered_junction_boxes = list_distance_circuits(&junction_boxes);

        let result = exercise1(&junction_boxes, &ordered_junction_boxes, 10)?;
        assert_eq!(40_usize, result);
        let result = exercise2(&junction_boxes, &ordered_junction_boxes)?;
        assert_eq!(25272_usize, result);

        Ok(())
    }
}
