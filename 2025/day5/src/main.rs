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
        get_total_fresh_ingredients(&mut reader, &mut ingredient_range)
    );

    while remove_overlap(&mut ingredient_range) {
        // keep collapsing overlaps until none remain
    }

    println!("Answer part2: {:?}", count_ingredients(&ingredient_range));

    Ok(())
}

fn get_total_fresh_ingredients<R: BufRead>(
    reader: &mut R,
    food: &mut FoodRange,
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

fn remove_overlap(ingredient_range: &mut FoodRange) -> bool {
    let mut ingredient_range_without_overlap: Vec<core::ops::RangeInclusive<usize>> = Vec::new();

    let mut found_overlap = false;
    let mut i = ingredient_range.clone();
    'outer: while let Some(one) = i.ranges.pop() {
        for two in i.ranges.iter() {
            if *one.start() >= *two.start() && *one.end() <= *two.end() {
                continue 'outer;
            }

            if *two.end() >= *one.start() && *one.end() >= *two.start() && one != *two {
                let st = usize::min(*one.start(), *two.start());
                let en = usize::max(*one.end(), *two.end());
                ingredient_range_without_overlap.push(st..=en);
                found_overlap = true;
                continue 'outer;
            }
        }

        ingredient_range_without_overlap.push(one);
    }

    *ingredient_range = FoodRange {
        ranges: ingredient_range_without_overlap,
    };

    found_overlap
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

        while remove_overlap(&mut ingredient_range) {}
        assert_eq!(14, count_ingredients(&ingredient_range));
    }
}
