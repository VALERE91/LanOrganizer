use wither::bson::DateTime;

use serde::{Serialize, Deserialize};
use uuid::Uuid;
use wither::{prelude::*, Result};
use wither::bson::{doc, oid::ObjectId};
use wither::mongodb::Client;

#[derive(Debug, Model, Serialize, Deserialize)]
#[model(index(keys=r#"doc!{"steam_id": 1}"#, options=r#"doc!{"unique": true}"#))]
pub struct Game{
    #[serde(rename = "_id", skip_serializing_if="Option::is_none")]
    pub id: Uuid,
    pub steam_id: u64,
    #[serde(rename = "_id", skip_serializing_if="Option::is_none")]
    pub name: String,
    #[serde(rename = "_id", skip_serializing_if="Option::is_none")]
    pub description: String,
    #[serde(rename = "_id", skip_serializing_if="Option::is_none")]
    pub image: String,
    #[serde(rename = "_id", skip_serializing_if="Option::is_none")]
    pub last_update: DateTime
}

