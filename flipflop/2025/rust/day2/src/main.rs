use std::{
    fs::File,
    io::{BufRead, BufReader},
};

fn main() -> Result<(), Box<dyn std::error::Error>> {
    let file = File::open("./input")?;
    let reader = BufReader::new(file);
    let line = reader.lines().next().unwrap()?;

    let result = find_max_height(&line);
    assert_eq!(152, result);
    let result = find_max_height2(&line);
    assert_eq!(1583, result);
    let result = find_max_height3(&line);
    assert_eq!(42797, result);
    Ok(())
}

fn find_max_height(input: &str) -> isize {
    input
        .chars()
        .fold((0, 0), |(height, max), c| {
            let new_height = height
                + match c {
                    '^' => 1,
                    'v' => -1,
                    _ => 0,
                };
            (new_height, new_height.max(max))
        })
        .1
}

fn find_max_height2(input: &str) -> isize {
    input
        .chars()
        .fold((0, 0, 0), |(height, max, streak), c| {
            let streak = match c {
                '^' if streak >= 0 => streak + 1,
                '^' => 1,
                'v' if streak <= 0 => streak - 1,
                'v' => -1,
                _ => 0,
            };
            let new_height = height + streak;
            (new_height, new_height.max(max), streak)
        })
        .1
}

fn find_max_height3(input: &str) -> isize {
    input
        .chars()
        .fold((0, 0, 0), |(height, max, streak), c| {
            let mut new_height = height;
            let new_streak = match c {
                '^' if streak > 0 => streak + 1,
                '^' => {
                    new_height = height - fib(streak);
                    1
                }
                'v' if streak < 0 => streak - 1,
                'v' => {
                    new_height = height + fib(streak);
                    -1
                }
                _ => 0,
            };
            (new_height, max.max(new_height), new_streak)
        })
        .1
}

fn fib(input: isize) -> isize {
    match input.abs() {
        0 => 0,
        1 => 1,
        n => fib(n - 1) + fib(n - 2),
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_max_height() {
        assert_eq!(6, find_max_height("^^^v^^^^vvvvvvv"));
    }

    #[test]
    fn test_max_height2() {
        assert_eq!(15, find_max_height2("^^^v^^^^vvvvvvv"));
    }

    #[test]
    fn test_max_height3() {
        assert_eq!(144, fib(12));
        assert_eq!(5, fib(5));
        // assert_eq!(5, count_streak("^^^^^")); // This case doesn't work!
        assert_eq!(4, find_max_height3("^^^v^^^^vvvvvvv"));
        assert_eq!(144, find_max_height3("^^^^^^^^^^^^vvvvvvvvv^"));
    }
}
