#[get("/")]
pub fn get_all() -> &'static str{
    return "Hello Games"
}