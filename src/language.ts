export const language = {
  // English Variants
  englishUS: 'en-US',
  englishGB: 'en-GB',
  englishAU: 'en-AU',
  englishCA: 'en-CA',
  englishIN: 'en-IN',

  // Spanish Variants
  spanishES: 'es-ES',
  spanishMX: 'es-MX',
  spanishUS: 'es-US',

  // French Variants
  frenchFR: 'fr-FR',
  frenchCA: 'fr-CA',

  // German Variants
  germanDE: 'de-DE',
  germanAT: 'de-AT',
  germanCH: 'de-CH',

  // Chinese Variants
  chineseSimplified: 'zh-CN',
  chineseTraditional: 'zh-TW',
  chineseHongKong: 'zh-HK',

  // Arabic Variants
  arabicSA: 'ar-SA',
  arabicAE: 'ar-AE',
  arabicEG: 'ar-EG',

  // Portuguese Variants
  portuguesePT: 'pt-PT',
  portugueseBR: 'pt-BR',

  // Hindi
  hindiIN: 'hi-IN',

  // Japanese
  japaneseJP: 'ja-JP',

  // Korean
  koreanKR: 'ko-KR',

  // Russian
  russianRU: 'ru-RU',

  // Italian
  italianIT: 'it-IT',

  // Dutch
  dutchNL: 'nl-NL',

  // Turkish
  turkishTR: 'tr-TR',

  // Vietnamese
  vietnameseVN: 'vi-VN',

  // Polish
  polishPL: 'pl-PL',

  // Swedish
  swedishSE: 'sv-SE',

  // Norwegian
  norwegianNO: 'no-NO',

  // Danish
  danishDK: 'da-DK',

  // Finnish
  finnishFI: 'fi-FI',

  // Greek
  greekGR: 'el-GR',

  // Hebrew
  hebrewIL: 'he-IL',

  // Indonesian
  indonesianID: 'id-ID',

  // Malay
  malayMY: 'ms-MY',

  // Thai
  thaiTH: 'th-TH',

  // Czech
  czechCZ: 'cs-CZ',

  // Hungarian
  hungarianHU: 'hu-HU',

  // Romanian
  romanianRO: 'ro-RO',

  // Bulgarian
  bulgarianBG: 'bg-BG',

  // Ukrainian
  ukrainianUA: 'uk-UA',

  // Bengali
  bengaliBD: 'bn-BD',
  bengaliIN: 'bn-IN',

  // Tamil
  tamilIN: 'ta-IN',

  // Telugu
  teluguIN: 'te-IN',

  // Marathi
  marathiIN: 'mr-IN',

  // Gujarati
  gujaratiIN: 'gu-IN',

  // Kannada
  kannadaIN: 'kn-IN',

  // Malayalam
  malayalamIN: 'ml-IN',

  // Punjabi
  punjabiIN: 'pa-IN',

  // Sinhala
  sinhalaLK: 'si-LK',
} as const;

export type LanguageKey = keyof typeof language;
export type LanguageTag = (typeof language)[LanguageKey];
