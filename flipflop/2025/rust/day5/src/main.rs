use std::fs;

fn main() {
    let input = fs::read_to_string("./input").unwrap();
    assert_eq!(1553, part1(&input));
    assert_eq!("GWaISOhXzdCTvPrm", part2(&input));
    assert_eq!(1553, part3(&input));
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

fn part2(input: &str) -> String {
    let mut i = 0;
    let mut visited = Vec::new();
    loop {
        let ch = input.chars().nth(i).unwrap();
        visited.push(ch);
        let begin = input.find(ch).unwrap();
        let end = input.rfind(ch).unwrap();

        if i == begin {
            i = end + 1;
        } else {
            i = begin + 1;
        }

        if i - 1 == input.len() - 1 {
            let mut result = Vec::new();
            input.chars().for_each(|c| {
                if !visited.contains(&c) && !result.contains(&c) {
                    result.push(c);
                }
            });
            return result.into_iter().collect();
        }
    }
}

fn part3(input: &str) -> isize {
    let mut result: isize = 0;
    let mut i = 0;
    loop {
        let ch = input.chars().nth(i).unwrap();
        let begin = input.find(ch).unwrap();
        let end = input.rfind(ch).unwrap();

        if ch.is_ascii_uppercase() {
            result -= (end - begin) as isize;
        } else {
            result += (end - begin) as isize;
        }

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

    #[test]
    fn test_part2() {
        let input = "ABccksiPiBAksP";
        assert_eq!("Bc", part2(input));
    }

    #[test]
    fn test_part3() {
        let input = "ABccksiPiBAksP";
        assert_eq!(-6, part3(input));
    }
}
