use std::fs::File;
use std::io::{self, BufRead};

#[derive(Debug, Default, Clone)]
struct FoodRange {
    ranges: Vec<core::ops::RangeInclusive<usize>>,
}

fn main() -> Result<(), Box<dyn std::error::Error>> {
    let file = File::open("./input")?;
    let mut reader = io::BufReader::new(file);

    let mut ingredient_range = FoodRange::default();
    get_ranges(&mut reader, &mut ingredient_range)?;
    println!(
        "Answer part1: {:?}",
        get_total_fresh_ingredients(&mut reader, &ingredient_range)
    );

    let mut changed = true;
    while changed {
        let (next, merged) = remove_overlap(ingredient_range);
        ingredient_range = next;
        changed = merged;
    }

    println!("Answer part2: {:?}", count_ingredients(&ingredient_range));

    Ok(())
}

fn get_total_fresh_ingredients<R: BufRead>(
    reader: &mut R,
    food: &FoodRange,
) -> Result<usize, Box<dyn std::error::Error>> {
    reader.lines().try_fold(0usize, |acc, line| {
        let value = line?.parse::<usize>()?;
        let hit = food.ranges.iter().any(|r| r.contains(&value));
        Ok(acc + usize::from(hit))
    })
}

fn count_ingredients(food: &FoodRange) -> usize {
    food.ranges.iter().map(|r| *r.end() - *r.start() + 1).sum()
}

fn remove_overlap(mut ingredient_range: FoodRange) -> (FoodRange, bool) {
    if ingredient_range.ranges.is_empty() {
        return (ingredient_range, false);
    }

    ingredient_range.ranges.sort_by_key(|range| *range.start());

    let mut merged: Vec<core::ops::RangeInclusive<usize>> = Vec::new();
    let mut changed = false;

    for range in ingredient_range.ranges.drain(..) {
        if let Some(last) = merged.last_mut() {
            if *range.start() <= *last.end() {
                let start = *last.start();
                let end = usize::max(*last.end(), *range.end());
                *last = start..=end;
                changed = true;
                continue;
            }
        }

        merged.push(range);
    }

    (FoodRange { ranges: merged }, changed)
}

fn get_ranges<R: BufRead>(
    reader: &mut R,
    food: &mut FoodRange,
) -> Result<(), Box<dyn std::error::Error>> {
    for line in reader.by_ref().lines() {
        let line = line?;
        if line.is_empty() {
            break;
        }

        let limits: Vec<&str> = line.split('-').collect();
        food.ranges
            .push(limits[0].parse::<usize>()?..=limits[1].parse::<usize>()?);
    }

    Ok(())
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

        let mut food = FoodRange::default();
        let mut reader = Cursor::new(input);
        get_ranges(&mut reader, &mut food).expect("Not proper input provided");
        let ingredients =
            get_total_fresh_ingredients(&mut reader, &mut food).expect("parse failed");
        assert_eq!(ingredients, 3);
    }

    #[test]
    fn default_example_part_2() {
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

        let mut ingredient_range = FoodRange::default();
        let mut reader = Cursor::new(input);
        get_ranges(&mut reader, &mut ingredient_range).expect("Not proper input provided");
        let _ =
            get_total_fresh_ingredients(&mut reader, &mut ingredient_range).expect("parse failed");

        let mut changed = true;
        while changed {
            let (next, merged) = remove_overlap(ingredient_range);
            ingredient_range = next;
            changed = merged;
        }
        assert_eq!(14, count_ingredients(&ingredient_range));
    }
}
