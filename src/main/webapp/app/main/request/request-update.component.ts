import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import { IRequestpoint } from '@/shared/model/requestpoint.model';

import AlertService from '@/shared/alert/alert.service';
import { IRequest, Request } from '@/shared/model/request.model';
import RequestService from './request.service';

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
  public request: IRequest = new Request();
  public isSaving = false;
  public currentLanguage = '';
  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.requestId) {
        vm.retrieveRequest(to.params.requestId);
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
    if (this.request.id) {
      this.requestService()
        .update(this.request)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('balbaursakApp.request.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.requestService()
        .create(this.request)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('balbaursakApp.request.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
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

  public initRelationships(): void {
    this.requestpointService()
      .retrieve()
      .then(res => {
        this.requestpoints = res.data;
      });
  }
}
