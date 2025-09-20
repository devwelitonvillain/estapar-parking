# Sistema de Gerenciamento de Estacionamento

## Observação:
A implementação para cobrança foi desenvolvida de acordo com as regras informadas, com os primeiros 30 minutos sendo gratuitos.
Dessa forma, existe uma divergência entre os valores da aplicação e os valores informados no simulador (aplicação em container).

## 📋 Descrição

O Sistema de Gerenciamento de Estacionamento é uma aplicação Spring Boot desenvolvida para gerenciar operações de estacionamento em tempo real. O sistema processa eventos de entrada, estacionamento e saída de veículos através de webhooks, calculando automaticamente tarifas e gerenciando receitas.

## 🏗️ Arquitetura

O projeto segue os princípios da **Clean Architecture** e está organizado em três módulos Maven:

### 📦 Módulos

- **`domain/`** - Camada de domínio contendo:
  - Entidades: `Garage`, `Spot`, `Vehicle`, `ParkingSession`, `Revenue`
  - Value Objects e Exceções de negócio
  - Interfaces de portas (ports)

- **`application/`** - Camada de aplicação contendo:
  - Casos de uso para eventos de estacionamento
  - DTOs e mapeadores
  - Lógica de negócio

- **`infrastructure/`** - Camada de infraestrutura contendo:
  - Controladores REST
  - Implementações de repositórios JPA
  - Configurações do Spring Boot
  - Migrações de banco de dados

## 🛠️ Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3.5.5**
- **Spring Data JPA**
- **MySQL 8.0+**
- **Flyway** (migrações de banco)
- **Maven** (gerenciamento de dependências)
- **Jackson** (serialização JSON)

## 📊 Modelo de Dados

O sistema gerencia as seguintes entidades principais:

- **Garages** - Setores de estacionamento com preços base
- **Spots** - Vagas individuais com coordenadas GPS
- **Vehicles** - Veículos identificados por placa
- **ParkingSession** - Sessões de estacionamento com status
- **Revenue** - Receitas geradas por sessão

## 🚀 Como Executar

### Pré-requisitos

1. **Java 21** ou superior
2. **Maven 3.6+**
3. **MySQL 8.0+**
4. **Git**

### 1. Configuração do Banco de Dados

```sql
-- Criar banco de dados
CREATE DATABASE estapar;
```

### 2. Clonagem e Configuração

```bash
# Clonar o repositório
git clone <repository-url>
cd parking-management

# Configurar variáveis de ambiente (opcional)
export DB_HOST=localhost
export DB_PORT=3306
export DB_NAME=estapar
export DB_USER=root
export DB_PASSWORD=estapar
```

### 3. Configuração da Aplicação

O arquivo `infrastructure/src/main/resources/application.yml` contém as configurações padrão:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/estapar?createDatabaseIfNotExist=true&serverTimezone=UTC
    username: root
    password: estapar
  
server:
  port: 3003
```

**Nota**: Ajuste as credenciais do banco conforme necessário.

### 4. Execução da Aplicação

```bash
# Compilar o projeto
./mvnw clean compile

# Executar a aplicação
./mvnw spring-boot:run -pl infrastructure

# Ou usando Java diretamente
./mvnw clean package
java -jar infrastructure/target/infrastructure-0.0.1-SNAPSHOT.jar
```

### 5. Verificação

A aplicação estará disponível em: `http://localhost:3003`

Para verificar se está funcionando:
```bash
curl -X GET http://localhost:3003/actuator/health
```

## 📡 API Endpoints

### Webhook de Eventos

**POST** `/webhook`

Processa eventos de estacionamento em tempo real.

#### Tipos de Eventos:

1. **ENTRY** - Entrada do veículo
```json
{
  "eventType": "ENTRY",
  "licensePlate": "ABC1234",
  "entryTime": "2024-01-15T10:30:00Z"
}
```

2. **PARKED** - Veículo estacionado
```json
{
  "eventType": "PARKED",
  "licensePlate": "ABC1234",
  "lat": -23.550520,
  "lng": -46.633308
}
```

3. **EXIT** - Saída do veículo
```json
{
  "eventType": "EXIT",
  "licensePlate": "ABC1234",
  "exitTime": "2024-01-15T12:30:00Z"
}
```

### Receitas

**GET** `/revenue?sector=A&date=YYYY-MM-DD`
- Consulta receita diária por setor

## 🗄️ Banco de Dados

### Migrações Flyway

As migrações estão localizadas em `infrastructure/src/main/resources/db/migration/`:

- `V1__Create_garages_table.sql`
- `V2__Create_spots_table.sql`
- `V3__Create_vehicles_table.sql`
- `V4__Create_parking_sessions_table.sql`
- `V5__Create_revenues_table.sql`

### Inicialização de Dados

O sistema inicializa automaticamente:
- Garages com setores A, B, C
- Spots distribuídos pelos setores
- Configurações de preços base

## 🧪 Testes

Por conta de tempo e imprevistos não foi possível adicionar testes unitários e de integração.

## 🔧 Desenvolvimento

### Estrutura de Pacotes

```
com.estapar/
├── model/entity/          # Entidades JPA
├── model/exception/       # Exceções de domínio
├── model/valueobject/     # Value Objects
├── port/in/              # Interfaces de entrada
├── port/out/             # Interfaces de saída
├── service/              # Serviços de domínio
├── usecase/              # Casos de uso
└── adapter/              # Adaptadores (web, persistence)
```

### Padrões Utilizados

- **Clean Architecture**
- **Hexagonal Architecture**
- **Repository Pattern**
- **Use Case Pattern**
- **Dependency Injection**
