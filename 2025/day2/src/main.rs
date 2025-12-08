use std::fs::File;
use std::io::{self, BufRead};

fn main() -> Result<(), Box<dyn std::error::Error>> {
    let file = File::open("./input")?;
    let reader = io::BufReader::new(file);

    let line = reader.lines().next().expect("empty file")?;

    let total: u64 = line
        .split(',')
        .flat_map(|range| {
            let (start, end) = range.split_once('-').expect("dash is mandatory");
            get_invalid_ids(start.parse::<u64>().unwrap(), end.parse::<u64>().unwrap())
        })
        .sum();

    println!("Result: {}", total);
    Ok(())
}

fn get_invalid_ids(start: u64, end: u64) -> Vec<u64> {
    (start..=end)
        .filter(|&elem| {
            let s = elem.to_string();
            let (l, r) = s.split_at(s.len() / 2);
            l == r
        })
        .collect()
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn example_test() {
        assert_eq!(vec![11, 22], get_invalid_ids(11, 22));
        assert_eq!(vec![99], get_invalid_ids(95, 115));
        assert_eq!(vec![1010], get_invalid_ids(998, 1012));
        assert_eq!(vec![1188511885], get_invalid_ids(1188511880, 1188511890));
        assert_eq!(vec![222222], get_invalid_ids(222220, 222224));
        assert_eq!(Vec::<u64>::new(), get_invalid_ids(1698522, 1698528));
        assert_eq!(vec![446446], get_invalid_ids(446443, 446449));
        assert_eq!(vec![38593859], get_invalid_ids(38593856, 38593862));
    }
}
