<%@ page contentType="text/html; charset=UTF-8" %>
<%
  response.setHeader("Cache-Control","no-store, no-cache, must-revalidate, max-age=0");
  response.setHeader("Pragma","no-cache");
  String ctx  = request.getContextPath();
  String e    = (String) request.getAttribute("e");
  String lock = (String) request.getAttribute("lock");
  
  
  Long lockUntil = (Long) session.getAttribute("OTP_LOCK_UNTIL");
  
  long remainMs = 0L;
  if (lockUntil != null) {
    long now = System.currentTimeMillis();
    remainMs = Math.max(0L, lockUntil - now);
  }
  boolean locked = remainMs > 0;
%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>Google OTP</title>
<meta name="viewport" content="width=device-width,initial-scale=1">

<style>
  :root{
    --bg1:#0f172a;        /* slate-900 */
    --bg2:#1e293b;        /* slate-800 */
    --card:#0b1223cc;     /* glassy */
    --ink:#e2e8f0;        /* slate-200 */
    --muted:#94a3b8;      /* slate-400 */
    --brand:#60a5fa;      /* sky-400 */
    --brand-2:#6366f1;    /* indigo-500 */
    --error:#f87171;      /* red-400 */
    --ok:#34d399;         /* emerald-400 */
    --ring:#93c5fd;       /* sky-300 */
    --shadow: 0 20px 60px rgba(2,6,23,.45);
    --radius: 20px;
  }

  /* background gradient */
  body{
    margin:0; min-height:100vh; color:var(--ink);
    background:
      radial-gradient(60rem 60rem at 10% -10%, #111827 10%, transparent 40%),
      radial-gradient(60rem 60rem at 110% 10%, #0b1324 10%, transparent 40%),
      linear-gradient(135deg, var(--bg1), var(--bg2));
    display:grid; place-items:center;
    font-family:-apple-system, Segoe UI, Roboto, Helvetica, Arial, Apple SD Gothic Neo, Noto Sans KR, "맑은 고딕", sans-serif;
  }

  .card{
    width:min(92vw, 440px);
    background:linear-gradient(180deg, rgba(255,255,255,0.06), rgba(255,255,255,0.02)) , var(--card);
    border:1px solid rgba(148,163,184,.2);
    border-radius:var(--radius);
    box-shadow:var(--shadow);
    backdrop-filter: blur(10px);
    padding:28px 24px 24px;
    position:relative;
    animation: pop .5s ease-out;
  }
  @keyframes pop { from{ transform:translateY(10px); opacity:0 } to{ transform:none; opacity:1 } }

  .title{
    display:flex; align-items:center; gap:12px; margin:0 0 8px;
    font-size:22px; font-weight:700; letter-spacing:.2px;
  }
  .badge{
    font-size:12px; color:#0b1223; background:linear-gradient(90deg, var(--brand), var(--brand-2));
    padding:4px 10px; border-radius:999px; font-weight:700;
  }
  .sub{ color:var(--muted); font-size:14px; margin:0 0 18px }

  /* alerts */
  .alert{ border-radius:14px; padding:10px 12px; margin:8px 0 14px; line-height:1.4; font-size:14px; }
  .alert.error{ background:rgba(248,113,113,.12); border:1px solid rgba(248,113,113,.35); color:#fecaca; }
  .alert.lock { background:rgba(96,165,250,.12); border:1px solid rgba(96,165,250,.35); color:#dbeafe; }

  form{ display:grid; gap:16px; }

  /* OTP segmented look (single input) */
  .otp-wrap{
    display:flex; justify-content:center;
  }
  .otp{
    width:240px; max-width:100%;
    letter-spacing:10px; text-align:center;
    font-size:28px; line-height:1; padding:14px 18px;
    color:var(--ink); background:rgba(2,6,23,.35);
    border:1px solid rgba(147,197,253,.35);
    border-radius:16px; outline:none;
    transition:border-color .2s, box-shadow .2s, transform .06s;
    caret-color:transparent;
    /* 6칸 가이드 */
    background-image:
      linear-gradient(to right, rgba(147,197,253,.35) 1px, transparent 1px),
      linear-gradient(to right, rgba(255,255,255,.04), rgba(255,255,255,.04));
    background-size: calc(100% / 6) 42px, 100% 42px;
    background-position: left center, center;
    background-repeat:no-repeat;
  }
  .otp:focus{
    border-color:var(--ring);
    box-shadow:0 0 0 4px rgba(147,197,253,.15);
    transform: translateY(-1px);
  }

  .actions{
    display:flex; gap:10px; justify-content:center; align-items:center;
  }
  .btn{
    cursor:pointer; border:none;
    padding:12px 16px; font-size:15px; font-weight:700;
    color:#0b1223;
    background:linear-gradient(90deg, var(--brand), var(--brand-2));
    border-radius:14px;
  }
  .btn:active{ transform:translateY(1px) }

  .link{color:#1f76e3;text-decoration:none;font-weight:600;font-size:14px}
  .link:hover{ text-decoration:underline }

  .footer{
    margin-top:16px; text-align:center; color:var(--muted); font-size:12px;
  }

  /* prefers-color-scheme light 대응(옵션) */
  @media (prefers-color-scheme: light){
    :root{ --ink:#0b1223; --card:#ffffffea; --shadow:0 20px 60px rgba(2,6,23,.08); }
    body{ color:var(--ink); }
    .alert.error{ color:#7f1d1d; background:#fee2e2; border-color:#fecaca; }
    .alert.lock{ color:#1e3a8a; background:#dbeafe; border-color:#bfdbfe; }
    .otp{ background:#f8fafc; border-color:#c7d2fe; }
  }
</style>
</head>
<body>
  <main class="card" role="main" aria-labelledby="otp-title">
    <h1 class="title" id="otp-title">
      🔐 Google OTP
      <span class="badge">2단계 인증</span>
    </h1>
    <p class="sub">인증 앱(Authenticator)에 표시된 <strong>6자리 코드</strong>를 입력하세요.</p>

    <% if ("1".equals(e)) { %>
      <div class="alert error" role="alert">코드가 올바르지 않습니다. 다시 시도해주세요.</div>
    <% } %> 
    <div id="locMsg" class="alert lock"  style="display:<%= locked ? "block" : "none" %>;" role="alert">잠시 후 다시 시도해주세요. 보안을 위해 입력이 일시적으로 제한되었습니다.</div>
    
    <div id="cd"
     style="display:<%= locked ? "block" : "none" %>;
            font-size:40px;font-weight:800;text-align:center;letter-spacing:2px;">
    </div>

    <div id="otp-ui" style="display:<%= locked ? "none" : "block" %>;">
    <form method="post" action="/login.do?method=otpVerify" autocomplete="off" id="otp-form">
      <!-- CSRF 토큰이 있다면 여기에 hidden으로 추가 -->
      <div class="otp-wrap">
        <input id="otp" name="code" type="text"
          inputmode="numeric" autocomplete="one-time-code"
          maxlength="6" pattern="[0-9]{6}" required
          class="otp" title="6자리 숫자를 입력하세요"
          aria-describedby="otp-hint"
          aria-invalid="<%= "1".equals(e) ? "true" : "false" %>">
      </div>
      <div class="actions">
        <button type="submit" class="btn">확인</button>
        <a href="/login.do?method=login" class="link">다시 로그인</a>
      </div>
    </form>

    <p id="otp-hint" class="footer">보안을 위해 여러 차례 실패하면 일정 시간 후에 다시 시도할 수 있습니다.</p>
  </main>

<script>
(function(){
  var el   = document.getElementById('otp');
  var form = document.getElementById('otp-form'); 
  if (!el) return;

  el.focus(); 
  
  
  (function(){
	  var cd = document.getElementById('cd');
	  var otpUI = document.getElementById('otp-ui');
	  var otpInput = document.getElementById('otp');
	  var locMsg = document.getElementById('locMsg');
	  var remain = <%= remainMs %>; // 서버 기준 남은 ms

	  function mmss(ms){
	    var t = Math.floor(ms/1000);
	    var m = Math.floor(t/60);
	    var s = t % 60;
	    return String(m).padStart(2,'0') + ':' + String(s).padStart(2,'0');
	  }

	  function showOtp(){
	    if (cd) cd.style.display = 'none';
	    if (otpUI) otpUI.style.display = 'block';
	    if (locMsg)locMsg.style.display = 'none';
	    if (otpInput) otpInput.focus();
	  }

	  // 초기 렌더
	  if (remain <= 0){
	    showOtp();
	    return;
	  } else {
	    cd.textContent = mmss(remain);
	  }

	  // 3) 1초씩 줄이고 00:00 되면 기존 OTP 화면으로 전환
	  var iv = setInterval(function(){
	    remain -= 1000;
	    if (remain <= 0){
	      clearInterval(iv);
	      cd.textContent = '00:00';
	      // 바로 기본 OTP 화면으로 전환
	      showOtp();
	      return;
	    }
	    cd.textContent = mmss(remain);
	  }, 1000);
	})();
  
  // 숫자만 허용 & 공백 제거
  el.addEventListener('input', function(){
    var v = el.value.replace(/\s+/g,'').replace(/[^0-9]/g,'').slice(0,6);
    if (v !== el.value) el.value = v;

    // 6자리 채우면 자동 제출 (원치 않으면 주석 처리)
    if (el.value.length === 6) {
      if (form.requestSubmit) form.requestSubmit();
      else form.submit();
    }
  });

  // 붙여넣기에도 숫자만
  el.addEventListener('paste', function(ev){
    ev.preventDefault();
    var t = (ev.clipboardData || window.clipboardData).getData('text') || '';
    t = t.replace(/\s+/g,'').replace(/[^0-9]/g,'').slice(0,6);
    document.execCommand('insertText', false, t);
  });

  // 주요 키만 허용(선택)
  el.addEventListener('keydown', function(e){
    var allow = [8,9,13,37,39,46]; // backspace, tab, enter, arrows, delete
    if (allow.indexOf(e.keyCode) >= 0) return;
    var isDigit = (e.keyCode >= 48 && e.keyCode <= 57) || (e.keyCode >= 96 && e.keyCode <= 105);
    if (!isDigit) e.preventDefault();
  });
})();
</script>
</body>
</html>
