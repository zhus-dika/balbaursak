<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="balbaursakApp.feedback.home.createOrEditLabel" v-text="$t('balbaursakApp.feedback.home.createOrEditLabel')">Create or edit a Feedback</h2>
                <div>
                    <div class="form-group" v-if="feedback.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="feedback.id" readonly />
                    </div>
                    <div class="form-group">
                        <label for="feedback-created" v-text="$t('balbaursakApp.feedback.created')">Created</label>
                        <input type="text" class="form-control" id="feedback-created" name="feedback-created"
                               v-model="feedback.created" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('balbaursakApp.feedback.customer')" for="feedback-customer">Customer</label>
                        <input type="text" class="form-control" name="customer" id="feedback-customer"
                            :class="{'valid': !$v.feedback.customer.$invalid, 'invalid': $v.feedback.customer.$invalid }" v-model="$v.feedback.customer.$model"  required/>
                        <div v-if="$v.feedback.customer.$anyDirty && $v.feedback.customer.$invalid">
                            <small class="form-text text-danger" v-if="!$v.feedback.customer.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('balbaursakApp.feedback.vote')" for="feedback-vote">Vote</label>
                        <input type="number" class="form-control" name="vote" id="feedback-vote"
                            :class="{'valid': !$v.feedback.vote.$invalid, 'invalid': $v.feedback.vote.$invalid }" v-model.number="$v.feedback.vote.$model"  required/>
                        <div v-if="$v.feedback.vote.$anyDirty && $v.feedback.vote.$invalid">
                            <small class="form-text text-danger" v-if="!$v.feedback.vote.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.feedback.vote.min" v-text="$t('entity.validation.min', {min: 0})">
                                This field should be at least 0.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.feedback.vote.max" v-text="$t('entity.validation.max', {max: 5})">
                                This field cannot be longer than 5 characters.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.feedback.vote.numeric" v-text="$t('entity.validation.number')">
                                This field should be a number.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('balbaursakApp.feedback.content')" for="feedback-content">Content</label>
                        <input type="text" class="form-control" name="content" id="feedback-content"
                            :class="{'valid': !$v.feedback.content.$invalid, 'invalid': $v.feedback.content.$invalid }" v-model="$v.feedback.content.$model"  required/>
                        <div v-if="$v.feedback.content.$anyDirty && $v.feedback.content.$invalid">
                            <small class="form-text text-danger" v-if="!$v.feedback.content.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="feedback-produce" v-text="$t('balbaursakApp.feedback.produce')">Produce</label>
                        <input type="text" class="form-control" id="feedback-produce" name="feedback-produce"
                               v-model="feedback.produce.name" readonly />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.feedback.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./feedback-update.component.ts">
</script>
