export interface IProduct {
  id?: number;
  name?: string;
  unit?: string;
}

export class Product implements IProduct {
  constructor(public id?: number, public name?: string, public unit?: string) {}
}
