<script lang="ts">
  async function submitLoginForm(this:HTMLFormElement) {
    const form: HTMLFormElement = this;    

    form.username.value = form.username.value.trim();
		if (form.username.value.length === 0) {
			alert('username 입력해');
			form.username.focus();
			return;
		}
		form.password.value = form.password.value.trim();
		console.log(form.password.value);
		if (form.password.value.length === 0) {
			alert('password 입력해');
			form.password.focus();
			return;
		}
		const rs = await fetch(`${import.meta.env.VITE_CORE_API_BASE_URL}/api/v1/members/login`, {
			method: 'POST',
			credentials: 'include', 
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify({
				username: form.username.value,
				password: form.password.value
			})
		}).then((res) => res.json());
		console.log(rs);
    
  }
</script>

<form on:submit|preventDefault={submitLoginForm}>
  <div>
    <label>
      <span>username</span>
      <input type="text" name="username" placeholder="username 써">
    </label>
  </div>
  <div>
    <label>
      <span>password</span>
      <input type="text" name="password" placeholder="password 써">
    </label>
  </div>

  <div>
    <label>
      <input type="submit" value="로그인">
    </label>
  </div>

</form>