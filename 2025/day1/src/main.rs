use std::fs::File;
use std::io::{self, BufRead};

fn main() -> Result<(), Box<dyn std::error::Error>> {
    let file = File::open("./input")?;
    let reader = io::BufReader::new(file);
    println!("{}", get_password(reader)?);
    Ok(())
}

fn get_password<R: BufRead>(reader: R) -> Result<u16, String> {
    let (hits, _) =
        reader
            .lines()
            .map_while(Result::ok)
            .try_fold((0_u16, 50_i32), |(hits, acc), line| {
                let (dir, n) = line.split_at(1);
                let step = n.parse::<i32>().map_err(|e| e.to_string())?;

                let next = match dir {
                    "R" => (acc + step) % 100,
                    "L" => (acc - step) % 100,
                    _ => return Err("invalid direction".to_string()),
                };

                let hits = hits + u16::from(next == 0);
                Ok((hits, next))
            })?;

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
}
