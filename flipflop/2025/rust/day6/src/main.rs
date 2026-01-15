use std::fs;

fn main() {
    let input = fs::read_to_string("./input").unwrap();

    let result = input
        .lines()
        .map(|l| calculate_position(l, 100, 1000))
        .filter(|b| (250..750).contains(&b.0) && (250..750).contains(&b.1))
        .count();

    assert_eq!(260, result);
}

fn calculate_position(bird: &str, seconds: isize, dim: isize) -> (isize, isize) {
    let (x, y) = bird.split_once(',').unwrap();
    let (speed_x, speed_y) = (x.parse::<isize>().unwrap(), y.parse::<isize>().unwrap());

    (
        (seconds * speed_x).rem_euclid(dim),
        (seconds * speed_y).rem_euclid(dim),
    )
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn distance() {
        let input = "1,1";
        let seconds = 10;
        let dim = 8;

        let position = calculate_position(input, seconds, dim);

        assert_eq!((2, 2), position);
    }
}
