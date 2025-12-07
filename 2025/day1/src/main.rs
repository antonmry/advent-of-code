use std::fs::File;
use std::io::{self, BufRead};

const DIAL_SIZE: i32 = 100;
const START_POS: i32 = 50;

fn main() -> Result<(), Box<dyn std::error::Error>> {
    let file = File::open("./input")?;
    let reader = io::BufReader::new(file);
    println!("Password: {}", get_password2(reader)?);
    Ok(())
}

#[allow(dead_code)]
fn get_password<R: BufRead>(reader: R) -> Result<u16, String> {
    let (hits, _) = reader.lines().map_while(Result::ok).try_fold(
        (0_u16, START_POS),
        |(hits, acc), line| {
            let (dir, n) = line.split_at(1);
            let step = n.parse::<i32>().map_err(|e| e.to_string())?;

            let next = match dir {
                "R" => (acc + step) % DIAL_SIZE,
                "L" => (acc - step) % DIAL_SIZE,
                _ => return Err("invalid direction".to_string()),
            };

            let hits = hits + u16::from(next == 0);
            Ok((hits, next))
        },
    )?;

    Ok(hits)
}

fn get_password2<R: BufRead>(reader: R) -> Result<u16, String> {
    let (hits, _) = reader.lines().map_while(Result::ok).try_fold(
        (0_u16, START_POS),
        |(hits, position), line| {
            let (dir, n) = line.split_at(1);
            let step = n.parse::<i32>().map_err(|e| e.to_string())?;

            let (next, rotations) = match dir {
                "R" => {
                    let total = position + step;
                    (total.rem_euclid(DIAL_SIZE), (total / DIAL_SIZE) as u16)
                }
                "L" => {
                    // Moving left: count how many times we pass 0.
                    // - Otherwise we cross once, plus one for each additional full turn.
                    let rotations = if position == 0 {
                        (step / DIAL_SIZE) as u16
                    // - If the step is smaller than our distance to 0, we never cross it.
                    } else if step < position {
                        0
                    // - If starting at 0, only full 100-step turns matter.
                    } else {
                        (1 + (step - position) / DIAL_SIZE) as u16
                    };
                    // New position after wrapping on the dial.
                    let next = (position - step).rem_euclid(DIAL_SIZE);
                    (next, rotations)
                }
                _ => return Err("invalid direction".to_string()),
            };

            Ok((hits + rotations, next))
        },
    )?;

    Ok(hits)
}

#[cfg(test)]
mod tests {
    use super::*;
    use std::io::Cursor;

    #[test]
    fn counts_hits_every_100() {
        let input = "R50\nL100\n";
        let hits = get_password(Cursor::new(input)).expect("parse failed");
        assert_eq!(hits, 2);
    }

    #[test]
    fn counts_hits_every_100_2() {
        let input = "R50\nL100\n";
        let hits = get_password2(Cursor::new(input)).expect("parse failed");
        assert_eq!(hits, 2);
    }

    #[test]
    fn counts_hits_right() {
        let input = "R1000\n";
        let hits = get_password2(Cursor::new(input)).expect("parse failed");
        assert_eq!(hits, 10);
    }

    #[test]
    fn counts_hits_left() {
        let input = "R50\nL100\nL1000\n";
        let hits = get_password2(Cursor::new(input)).expect("parse failed");
        assert_eq!(hits, 12);
    }

    #[test]
    fn counts_hits_from_example() {
        let input = "L68\nL30\nR48\nL5\nR60\nL55\nL1\nL99\nR14\nL82\n";
        let hits = get_password2(Cursor::new(input)).expect("parse failed");
        assert_eq!(hits, 6);
    }

    #[test]
    fn counts_hit_when_landing_exactly_on_zero_left() {
        let input = "L50\n";
        let hits = get_password2(Cursor::new(input)).expect("parse failed");
        assert_eq!(hits, 1);
    }
}
