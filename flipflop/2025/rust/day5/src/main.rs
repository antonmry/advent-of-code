use std::fs;

fn main() {
    let input = fs::read_to_string("./input").unwrap();
    assert_eq!(1553, part1(&input));
}

fn part1(input: &str) -> usize {
    let mut result = 0;
    let mut i = 0;
    loop {
        let ch = input.chars().nth(i).unwrap();
        let begin = input.find(ch).unwrap();
        let end = input.rfind(ch).unwrap();

        result += end - begin;

        if i == begin {
            i = end + 1;
        } else {
            i = begin + 1;
        }

        if i - 1 == input.len() - 1 {
            return result;
        }
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_part1() {
        let input = "ABccksiPiBAksP";
        assert_eq!(38, part1(input));
    }
}
