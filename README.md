# Huertos Urbanos

Backend base for the community garden platform. The repository contains two Spring Boot services:

- **Parcel service** (`http://localhost:8081`): parcel creation, listing, detail, update, and deletion.
- **Sensor service** (`http://localhost:8082`): environmental reading creation and lookup, including filtering by parcel.

Each service owns a separate PostgreSQL database (`parcel_db` and `sensor_db`). The sensor service checks that a parcel exists by calling the parcel service; it does not share parcel tables.

## Requirements

- Java 17 or newer
- Maven 3.9+
- Docker with Docker Compose

## Run with Docker Compose

```powershell
docker compose up --build
```

The database is exposed on port `5432`. For local development only, Compose defaults to `local-dev-password`; set `POSTGRES_PASSWORD` in the shell or an untracked `.env` file before using this beyond a local machine. PostgreSQL's initialization script runs only when the data volume is first created.

## Build and test

```powershell
mvn test
```

## API

### Parcels

- `POST /api/v1/parcels`
- `GET /api/v1/parcels`
- `GET /api/v1/parcels/{id}`
- `PUT /api/v1/parcels/{id}`
- `DELETE /api/v1/parcels/{id}`

Example:

```json
{
  "name": "Huerto Norte",
  "location": { "latitude": 28.6353, "longitude": -106.0889 },
  "soilType": "LOAMY",
  "ownerOrLead": "Ana López"
}
```

Supported `soilType` values: `CLAY`, `LOAMY`, `SANDY`.

### Sensor readings

- `POST /api/v1/readings`
- `GET /api/v1/readings` (optional `parcelId` query parameter)
- `GET /api/v1/readings/{id}`

Example:

```json
{
  "parcelId": "00000000-0000-0000-0000-000000000001",
  "type": "SOIL_MOISTURE",
  "value": 42.5,
  "unit": "%"
}
```

Supported types: `SOIL_MOISTURE`, `AIR_TEMPERATURE`, `SOIL_PH`, `NITROGEN`, `PHOSPHORUS`, `POTASSIUM`, `AIR_QUALITY`. The service records `recordedAt` when it receives a reading.

Both services validate request data, normalize user-provided text, reject markup/control characters, and return structured JSON errors. Invalid enum values and malformed JSON receive a client error response rather than an internal server error.

## Git workflow for this project

The implementation branch is local: `feature/urban-garden-services`. Commit coherent milestones on that branch; only push it if you want a remote backup. The repository's current default branch is lowercase `main` (the final-delivery branch referred to as MAIN in the activity). Do not push `main` during development. After feature work, merge the feature branch into `QA`, run acceptance checks there, then merge the tested result into `DEV` and push `DEV`. Keep `main` untouched until the project is accepted as complete, then merge the approved result and push `main`.
