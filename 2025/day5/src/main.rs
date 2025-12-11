use std::fs::File;
use std::io::{self, BufRead};

#[derive(Debug, Default, Clone)]
struct FoodRange {
    ranges: Vec<core::ops::RangeInclusive<usize>>,
}

fn main() -> Result<(), Box<dyn std::error::Error>> {
    let file = File::open("./input")?;
    let reader = io::BufReader::new(file);
    println!("{:?}", get_total_fresh_ingredients(reader));
    Ok(())
}

fn get_total_fresh_ingredients<R: BufRead>(
    mut reader: R,
) -> Result<usize, Box<dyn std::error::Error>> {
    let mut food = FoodRange::default();
    for line in reader.by_ref().lines() {
        let line = line?;
        if line.is_empty() {
            break;
        }

        let limits: Vec<&str> = line.split('-').collect();
        food.ranges
            .push(limits[0].parse::<usize>()?..=limits[1].parse::<usize>()?);
    }

    reader.lines().try_fold(0usize, |acc, line| {
        let value = line?.parse::<usize>()?;
        let hit = food.ranges.iter().any(|r| r.contains(&value));
        Ok(acc + usize::from(hit))
    })
}

#[cfg(test)]
mod tests {
    use std::io::Cursor;

    use super::*;

    #[test]
    fn default_example() {
        let input = "3-5
10-14
16-20
12-18

1
5
8
11
17
32";
        let ingredients = get_total_fresh_ingredients(Cursor::new(input)).expect("parse failed");
        assert_eq!(ingredients, 3);
    }
}
