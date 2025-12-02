const fs = require('fs');
const path = require('path');

const lang = 'pt_br';

const sourceDir = path.join(__dirname, 'webapp', 'src', 'assets', 'i18n', lang);
const outputDir = path.join(__dirname, 'webapp', 'src', 'assets', 'i18n');
const outputFile = `${lang}.json`;

const merged = {};

fs.readdirSync(sourceDir).forEach(file => {
  if (file.endsWith('.json')) {
    const content = JSON.parse(fs.readFileSync(path.join(sourceDir, file), 'utf8'));
    deepMerge(merged, content);
  }
});

fs.writeFileSync(
  path.join(outputDir, outputFile),
  JSON.stringify(merged, null, 2),
  'utf8'
);

function deepMerge(target, source) {
  for (const key of Object.keys(source)) {
    if (
      source[key] !== null &&
      typeof source[key] === 'object' &&
      !Array.isArray(source[key])
    ) {
      if (!target[key]) target[key] = {};
      deepMerge(target[key], source[key]);
    } else {
      target[key] = source[key];
    }
  }
}
