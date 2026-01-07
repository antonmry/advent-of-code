use std::{
    fs::File,
    io::{BufRead, BufReader},
};

fn main() -> Result<(), Box<dyn std::error::Error>> {
    let file = File::open("./input")?;
    let reader = BufReader::new(file);

    let result: usize = reader
        .lines()
        .map_while(|l| l.ok())
        .filter(|l| l.contains('x'))
        .filter(|l| {
            let mut line = l.split_whitespace();
            let mut dim_parts = line.next().unwrap().trim_end_matches(':').split('x');
            let w: usize = dim_parts.next().unwrap().parse().unwrap();
            let h: usize = dim_parts.next().unwrap().parse().unwrap();

            let box_count: usize = line.map(|d| d.parse::<usize>().unwrap()).sum();

            box_count * 9 <= w * h
        })
        .count();

    println!("{result}");
    assert_eq!(548, result);

    Ok(())
}
