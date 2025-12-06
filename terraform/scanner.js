require('dotenv').config();

const scanner = require('@sonar/scan');

scanner(
  {
    serverUrl: 'http://localhost:9000',
    token: process.env.SONAR_TOKEN,
    options: {
      'sonar.projectKey': 'Proyecto',
    },
  },
  () => process.exit()
);