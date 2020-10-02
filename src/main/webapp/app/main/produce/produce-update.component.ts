import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { maxLength, minValue, numeric, required } from 'vuelidate/lib/validators';

import CategoryService from '../category/category.service';
import { ICategory } from '@/shared/model/category.model';

import FeedbackService from '../feedback/feedback.service';
import { IFeedback } from '@/shared/model/feedback.model';

import AlertService from '@/shared/alert/alert.service';
import { IProduce, Produce } from '@/shared/model/produce.model';
import ProduceService from './produce.service';

const validations: any = {
  produce: {
    name: {
      required,
    },
    unit: {
      required,
    },
    contains: {
      required,
      maxLength: maxLength(1024),
    },
    days: {
      required,
      numeric,
      min: minValue(0),
    },
    description: {
      required,
      maxLength: maxLength(1024),
    },
    price: {
      required,
      numeric,
      min: minValue(0),
    },
    file: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class ProduceUpdate extends mixins(JhiDataUtils) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('produceService') private produceService: () => ProduceService;
  public produce: IProduce = new Produce();

  @Inject('categoryService') private categoryService: () => CategoryService;

  public categories: ICategory[] = [];

  @Inject('feedbackService') private feedbackService: () => FeedbackService;

  public feedbacks: IFeedback[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.produceId) {
        vm.retrieveProduce(to.params.produceId);
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
    if (this.produce.id) {
      this.produceService()
        .update(this.produce)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('balbaursakApp.produce.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.produceService()
        .create(this.produce)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('balbaursakApp.produce.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveProduce(produceId): void {
    this.produceService()
      .find(produceId)
      .then(res => {
        this.produce = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public clearInputImage(field, fieldContentType, idInput): void {
    if (this.produce && field && fieldContentType) {
      if (this.produce.hasOwnProperty(field)) {
        this.produce[field] = null;
      }
      if (this.produce.hasOwnProperty(fieldContentType)) {
        this.produce[fieldContentType] = null;
      }
      if (idInput) {
        (<any>this).$refs[idInput] = null;
      }
    }
  }

  public initRelationships(): void {
    this.categoryService()
      .retrieve()
      .then(res => {
        this.categories = res.data;
      });
    this.feedbackService()
      .retrieve()
      .then(res => {
        this.feedbacks = res.data;
      });
  }
}
