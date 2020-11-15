import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import ProduceService from '../produce/produce.service';
import { IProduce } from '@/shared/model/produce.model';

import RequestService from '../request/request.service';
import { IRequest } from '@/shared/model/request.model';

import AlertService from '@/shared/alert/alert.service';
import { IRequestpoint, Requestpoint } from '@/shared/model/requestpoint.model';
import RequestpointService from './requestpoint.service';

const validations: any = {
  requestpoint: {
    quantity: {
      required,
      numeric,
      min: minValue(0),
    },
    total: {
      required,
      numeric,
      min: minValue(0),
    },
    produce: {
      required,
    },
    request: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class RequestpointUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('requestpointService') private requestpointService: () => RequestpointService;
  public requestpoint: IRequestpoint = new Requestpoint();

  @Inject('produceService') private produceService: () => ProduceService;

  public produces: IProduce[] = [];

  @Inject('requestService') private requestService: () => RequestService;

  public requests: IRequest[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.requestpointId) {
        vm.retrieveRequestpoint(to.params.requestpointId);
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
    if (this.requestpoint.id) {
      this.requestpointService()
        .update(this.requestpoint)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('balbaursakApp.requestpoint.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.requestpointService()
        .create(this.requestpoint)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('balbaursakApp.requestpoint.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveRequestpoint(requestpointId): void {
    this.requestpointService()
      .find(requestpointId)
      .then(res => {
        this.requestpoint = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.produceService()
      .retrieve()
      .then(res => {
        this.produces = res.data;
      });
    this.requestService()
      .retrieve()
      .then(res => {
        this.requests = res.data;
      });
  }
}
