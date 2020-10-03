<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="balbaursakApp.request.home.createOrEditLabel" v-text="$t('balbaursakApp.request.home.createOrEditLabel')">Create or edit a Request</h2>
                <div>
                    <div class="form-group" v-if="request.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="request.id" readonly />
                    </div>
                    <div class="form-group">
                        <label for="request-created" v-text="$t('balbaursakApp.request.created')">Created</label>
                        <input type="text" class="form-control" id="request-created" name="request-created"
                               v-model="request.created" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('balbaursakApp.request.customer')" for="request-customer">Customer</label>
                        <input type="text" class="form-control" name="customer" id="request-customer"
                            :class="{'valid': !$v.request.customer.$invalid, 'invalid': $v.request.customer.$invalid }" v-model="$v.request.customer.$model"  required/>
                        <div v-if="$v.request.customer.$anyDirty && $v.request.customer.$invalid">
                            <small class="form-text text-danger" v-if="!$v.request.customer.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('balbaursakApp.request.phone')" for="request-phone">Phone</label>
                        <vue-tel-input  class="form-control" name="phone" id="request-phone" :class="{'valid': !$v.request.phone.$invalid, 'invalid': $v.request.phone.$invalid }" v-model="$v.request.phone.$model"  required>
                        </vue-tel-input>
                        <div v-if="$v.request.phone.$anyDirty && $v.request.phone.$invalid">
                            <small class="form-text text-danger" v-if="!$v.request.phone.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="request-total" v-text="$t('balbaursakApp.request.total')">Total</label>
                        <input type="text" class="form-control" id="request-total" name="total"
                               v-model="request.total" readonly />
                    </div>
                    <div class="form-group">
                        <label for="request-created" v-text="$t('balbaursakApp.request.sales')">Sales</label>
                        <input type="text" class="form-control" id="request-sales" name="sales"
                               v-model="request.sales" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('balbaursakApp.request.address')" for="request-address">Address</label>
                        <input type="text" class="form-control" name="address" id="request-address"
                            :class="{'valid': !$v.request.address.$invalid, 'invalid': $v.request.address.$invalid }" v-model="$v.request.address.$model"  required/>
                        <div v-if="$v.request.address.$anyDirty && $v.request.address.$invalid">
                            <small class="form-text text-danger" v-if="!$v.request.address.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.request.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./request-update.component.ts">
</script>
