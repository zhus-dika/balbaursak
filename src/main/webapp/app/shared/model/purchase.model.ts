import { IProduct } from '@/shared/model/product.model';

export interface IPurchase {
  id?: number;
  created?: Date;
  quantity?: number;
  price?: number;
  total?: number;
  product?: IProduct;
}

export class Purchase implements IPurchase {
  constructor(
    public id?: number,
    public created?: Date,
    public quantity?: number,
    public price?: number,
    public total?: number,
    public product?: IProduct
  ) {}
}
