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
            get_invalid_ids2(start.parse::<u64>().unwrap(), end.parse::<u64>().unwrap())
        })
        .sum();

    println!("Result: {}", total);
    Ok(())
}

#[allow(dead_code)]
fn get_invalid_ids(start: u64, end: u64) -> Vec<u64> {
    (start..=end)
        .filter(|&elem| {
            let s = elem.to_string();
            let (l, r) = s.split_at(s.len() / 2);
            l == r
        })
        .collect()
}

fn get_invalid_ids2(start: u64, end: u64) -> Vec<u64> {
    (start..=end).filter(|&elem| is_invalid_id(elem)).collect()
}

fn is_invalid_id(elem: u64) -> bool {
    let s = elem.to_string();
    let d = s.len();

    // Candidate chunk counts (number of chunks) are the divisors of d.
    // For each divisor x of d, the chunk size will be f = d / x.
    let fs: Vec<u64> = (2..=d)
        .filter(|x| d % x == 0)
        .map(|x| (d / x) as u64)
        .collect();

    // For each candidate chunk size, split the digits and check if all chunks match.
    fs.into_iter().any(|f| {
        // Chunk the byte representation; digits are ASCII so byte boundaries are safe.
        let mut chunks = s.as_bytes().chunks(f as usize);
        match (chunks.next(), chunks.next()) {
            // Need at least two chunks; first two must match, and the rest must also match.
            (Some(first), Some(second)) if second == first => chunks.all(|c| c == first),
            // Not enough chunks or mismatch.
            _ => false,
        }
    })
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn check_invalid_ids() {
        assert!(is_invalid_id(11));
        assert!(is_invalid_id(111));
    }

    #[test]
    fn check_valid_ids() {
        assert!(!is_invalid_id(1));
        assert!(!is_invalid_id(101));
    }

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

    #[test]
    fn additional_examples() {
        assert_eq!(vec![11, 22], get_invalid_ids2(11, 22));
        assert_eq!(vec![99, 111], get_invalid_ids2(95, 115));
        assert_eq!(vec![999, 1010], get_invalid_ids2(998, 1012));
        assert_eq!(vec![1188511885], get_invalid_ids2(1188511880, 1188511890));
        assert_eq!(vec![222222], get_invalid_ids2(222220, 222224));
        assert_eq!(Vec::<u64>::new(), get_invalid_ids2(1698522, 1698528));
        assert_eq!(vec![446446], get_invalid_ids2(446443, 446449));
        //assert_eq!(vec![38593859], get_invalid_ids2(38593856, 38593862));
        assert_eq!(vec![565656], get_invalid_ids2(565653, 565659));
        assert_eq!(vec![824824824], get_invalid_ids2(824824821, 824824827));
        //assert_eq!(vec![2121212121], get_invalid_ids2(2121212118, 2121212124));
    }
}
