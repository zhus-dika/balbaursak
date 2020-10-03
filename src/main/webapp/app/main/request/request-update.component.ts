import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import { IRequestpoint } from '@/shared/model/requestpoint.model';

import AlertService from '@/shared/alert/alert.service';
import { IRequest, Request } from '@/shared/model/request.model';
import RequestService from './request.service';
import ProduceService from '../produce/produce.service';
import { IProduce } from '@/shared/model/produce.model';
import RequestpointService from '../request-points/requestpoint.service';

const validations: any = {
  request: {
    created: {
      required,
    },
    customer: {
      required,
    },
    phone: {
      required,
    },
    total: {
      required,
      numeric,
      min: minValue(0),
    },
    complete: {
      required,
    },
    sales: {
      numeric,
      min: minValue(0),
    },
    address: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class RequestUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('requestService') private requestService: () => RequestService;
  @Inject('produceService') private produceService: () => ProduceService;
  @Inject('requestpointService') private requestpointService: () => RequestpointService;

  public request: IRequest = new Request();
  public produce: IProduce = {};
  public isSaving = false;
  public currentLanguage = '';
  public requestpoints: IRequestpoint[] = [];
  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.requestId) {
        vm.retrieveRequest(to.params.requestId);
      }
    });
  }
  async created() {
    await this.initData();
    await this.retrieveAllRequestpoints();
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }
  public retrieveAllRequestpoints(): void {
    const bucket = this.$store.getters.bucket;
    this.request.total = 0;
    bucket.forEach((ele, idx) => {
      const produceId = parseInt(ele.key.substr(13), 10);
      this.produceService()
        .find(produceId)
        .then(res => {
          this.produce = res;
          const requestPoint = {
            id: idx + 1,
            quantity: parseInt(ele.val, 10),
            total: this.produce.price * parseInt(ele.val, 10),
            produce: this.produce
          };
          this.requestpoints.push(requestPoint);
          this.request.total = this.request.total + this.produce.price * parseInt(ele.val, 10);
        });
    });
    this.request.total = this.request.total * (1 - this.request.sales / 100);
  }
  public initData() {
    this.request.created = new Date();
    this.request.sales = 0;
    this.request.complete = 'created';
  }
  public save(): void {
    this.isSaving = true;
    this.requestService()
      .create(this.request)
      .then(param => {
        this.requestpoints.forEach(ele => {
          ele.id = undefined;
          ele.request = param;
          this.requestpointService()
            .create(ele)
            .then(res => {
              console.log(res);
            });
        });
        this.isSaving = false;
        this.requestpoints = [];
        this.$store.commit('removeall');
        this.$router.replace('/');
        const message = this.$t('balbaursakApp.request.created', { param: param.id });
        this.alertService().showAlert(message, 'success');
      });
  }

  public retrieveRequest(requestId): void {
    this.requestService()
      .find(requestId)
      .then(res => {
        this.request = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }
}
