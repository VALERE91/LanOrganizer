mod steam;
mod games;
mod data;

#[macro_use] extern crate rocket;

#[get("/")]
fn index() -> &'static str {
    "Hello, world!"
}

#[rocket::main]
async fn main() -> Result<(), rocket::Error> {
    let _rocket = rocket::build()
        .mount("/games", routes![
            crate::games::game_api::get_all
        ])
        .launch()
        .await?;

    Ok(())
}