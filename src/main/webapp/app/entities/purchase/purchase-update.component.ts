import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import ProductService from '../product/product.service';
import { IProduct } from '@/shared/model/product.model';

import AlertService from '@/shared/alert/alert.service';
import { IPurchase, Purchase } from '@/shared/model/purchase.model';
import PurchaseService from './purchase.service';

const validations: any = {
  purchase: {
    created: {
      required,
    },
    quantity: {
      required,
      numeric,
      min: minValue(0),
    },
    price: {
      required,
      numeric,
      min: minValue(0),
    },
    total: {
      required,
      numeric,
      min: minValue(0),
    },
    product: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class PurchaseUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('purchaseService') private purchaseService: () => PurchaseService;
  public purchase: IPurchase = new Purchase();

  @Inject('productService') private productService: () => ProductService;

  public products: IProduct[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.purchaseId) {
        vm.retrievePurchase(to.params.purchaseId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.purchase.id) {
      this.purchaseService()
        .update(this.purchase)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('balbaursakApp.purchase.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.purchaseService()
        .create(this.purchase)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('balbaursakApp.purchase.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrievePurchase(purchaseId): void {
    this.purchaseService()
      .find(purchaseId)
      .then(res => {
        this.purchase = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.productService()
      .retrieve()
      .then(res => {
        this.products = res.data;
      });
  }
}
