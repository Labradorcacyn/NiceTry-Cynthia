export class ChampionDto {
  name: string;
  cost: number;
  traitsUuid: number[];
  file: string;
  constructor() {
      this.name = '';
      this.cost = 0;
      this.traitsUuid = [];
      this.file = 'https://user-images.githubusercontent.com/24848110/33519396-7e56363c-d79d-11e7-969b-09782f5ccbab.png';
  }
}
