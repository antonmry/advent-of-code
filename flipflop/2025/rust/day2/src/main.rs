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
    Ok(())
}

fn find_max_height(input: &str) -> i32 {
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

fn find_max_height2(input: &str) -> i32 {
    input
        .chars()
        .fold((0, 0, 0), |(height, max, buffer), c| {
            let new_buffer = match c {
                '^' => {
                    if buffer >= 0 {
                        buffer + 1
                    } else {
                        1
                    }
                }
                'v' => {
                    if buffer <= 0 {
                        buffer - 1
                    } else {
                        -1
                    }
                }
                _ => 0,
            };
            let new_height = height + new_buffer;
            (new_height, new_height.max(max), new_buffer)
        })
        .1
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
}
