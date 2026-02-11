<%@ page contentType="text/html; charset=UTF-8" %>
<%
  response.setHeader("Cache-Control","no-store, no-cache, must-revalidate, max-age=0");
  response.setHeader("Pragma","no-cache");
  String ctx = request.getContextPath();
  String otpUri       = (String) request.getAttribute("otpUri");
  String base32Secret = (String) request.getAttribute("base32Secret");
  String e            = (String) request.getAttribute("e");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>Google OTP 등록</title>
<meta name="viewport" content="width=device-width,initial-scale=1">

<style>
  :root{
    --bg1:#0f172a; --bg2:#1e293b; /* slate 계열 */
    --card:#0b1223cc; --ink:#e2e8f0; --muted:#94a3b8;
    --brand:#60a5fa; --brand-2:#6366f1; --ring:#93c5fd;
    --error:#fca5a5; --ok:#34d399;
    --shadow:0 20px 60px rgba(2,6,23,.45); --radius:20px;
  }
  body{
    margin:0; min-height:100vh; color:var(--ink);
    background:
      radial-gradient(60rem 60rem at 10% -10%, #111827 10%, transparent 40%),
      radial-gradient(60rem 60rem at 110% 10%, #0b1324 10%, transparent 40%),
      linear-gradient(135deg, var(--bg1), var(--bg2));
    display:grid; place-items:center;
    font-family:-apple-system, Segoe UI, Roboto, Helvetica, Arial,"" Apple SD Gothic Neo, Noto Sans KR, "맑은 고딕", sans-serif;
  }
  .card{
    width:min(92vw, 520px);
    background:linear-gradient(180deg, rgba(255,255,255,0.06), rgba(255,255,255,0.02)), var(--card);
    border:1px solid rgba(148,163,184,.2);
    border-radius:var(--radius); box-shadow:var(--shadow);
    backdrop-filter: blur(10px);
    padding:26px 24px 22px; animation:pop .5s ease-out;
  }
  @keyframes pop{from{transform:translateY(10px);opacity:0}to{transform:none;opacity:1}}
  .title{display:flex;align-items:center;gap:12px;margin:0 0 8px;font-size:22px;font-weight:800;}
  .badge{font-size:12px;color:#0b1223;background:linear-gradient(90deg,var(--brand),var(--brand-2));padding:4px 10px;border-radius:999px;font-weight:700;}
  .sub{color:var(--muted);font-size:14px;margin:0 0 18px}
  ol.guide{margin:0 0 16px 18px;color:var(--ink);line-height:1.6}
  .qr-box{display:grid;place-items:center;margin:14px 0 12px}
  .qr{
    width:220px;height:220px;object-fit:contain;
    border-radius:16px;background:#fff;padding:12px;
    box-shadow:0 10px 32px rgba(2,6,23,.35), inset 0 0 0 1px rgba(0,0,0,.06);
  }
  .secret{
    display:flex;gap:8px;align-items:center;justify-content:center;flex-wrap:wrap;
    font-family:ui-monospace, SFMono-Regular, Menlo, Consolas, "Liberation Mono", monospace;
    background:rgba(2,6,23,.35); border:1px solid rgba(147,197,253,.35);
    border-radius:12px; padding:10px 12px; color:#dbeafe
  }
  .copy{cursor:pointer;border:none;padding:8px 10px;font-size:13px;font-weight:700;color:#0b1223;background:linear-gradient(90deg,var(--brand),var(--brand-2));border-radius:10px}
  form{display:grid;gap:16px;margin-top:10px}
  .label{font-size:14px;color:var(--muted)}
  .otp-wrap{display:flex;justify-content:center}
  .otp{
    width:260px;max-width:100%;letter-spacing:10px;text-align:center;
    font-size:28px;padding:14px 18px;color:var(--ink);background:rgba(2,6,23,.35);
    border:1px solid rgba(147,197,253,.35);border-radius:16px;outline:none;
    transition:border-color .2s, box-shadow .2s, transform .06s; caret-color:transparent;
    /* 6칸 가이드 */
    background-image:
      linear-gradient(to right, rgba(147,197,253,.35) 1px, transparent 1px),
      linear-gradient(to right, rgba(255,255,255,.04), rgba(255,255,255,.04));
    background-size: calc(100% / 6) 42px, 100% 42px;
    background-position: left center, center; background-repeat:no-repeat;
  }
  .otp:focus{border-color:var(--ring);box-shadow:0 0 0 4px rgba(147,197,253,.15);transform:translateY(-1px)}
  .actions{display:flex;gap:10px;justify-content:center;align-items:center}
  .btn{cursor:pointer;border:none;padding:12px 16px;font-size:15px;font-weight:700;color:#0b1223;background:linear-gradient(90deg,var(--brand),var(--brand-2));border-radius:14px}
  .link{color:#bfdbfe;text-decoration:none;font-weight:600;font-size:14px}
  .link:hover{text-decoration:underline}
  .alert{border-radius:14px;padding:10px 12px;margin:8px 0 0;line-height:1.4;font-size:14px}
  .alert.error{background:rgba(248,113,113,.12);border:1px solid rgba(248,113,113,.35);color:#fecaca}
  .foot{margin-top:8px;text-align:center;color:var(--muted);font-size:12px}

  @media (prefers-color-scheme: light){
    :root{--ink:#0b1223;--card:#ffffffea;--shadow:0 20px 60px rgba(2,6,23,.08)}
    .alert.error{color:#7f1d1d;background:#fee2e2;border-color:#fecaca}
    .otp{background:#f8fafc;border-color:#c7d2fe}
    .secret{background:#f8fafc;border-color:#c7d2fe;color:#0b1223}
    .link{color:#1d4ed8}
  }
</style>
</head>
<body>
<main class="card" role="main" aria-labelledby="otp-title">
  <h1 class="title" id="otp-title">📲 Google OTP 등록 <span class="badge">1회 설정</span></h1>
  <p class="sub">인증 앱(Authenticator)으로 계정을 추가하고, 표시되는 <strong>6자리 코드</strong>를 입력해 등록을 마치세요.</p>

  <ol class="guide">
    <li>휴대폰에 <strong>Google Authenticator</strong> 설치</li>
    <li>아래 QR 코드를 스캔해 계정 추가</li>
    <li>앱에 표시된 6자리 코드를 입력 후 등록 완료</li>
  </ol>

  <div class="qr-box">
    <img class="qr" alt="OTP QR" src="<%= ctx %>/qr?data=<%= java.net.URLEncoder.encode(otpUri, "UTF-8") %>">
  </div>

  <div class="secret">
    <span>수동 입력 시크릿:</span>
    <code id="secret"><%= base32Secret %></code>
    <button type="button" class="copy" id="copy-btn">복사</button>
  </div>

  <% if ("1".equals(e)) { %>
    <div class="alert error" role="alert">코드가 맞지 않습니다. 다시 시도해주세요.</div>
  <% } %>

  <form method="post" action="<%= ctx %>/login.do?method=otpEnrollVerify" autocomplete="off" id="enroll-form">
    <!-- CSRF 토큰이 있다면 hidden으로 추가하세요 -->
    <label for="code" class="label">앱의 6자리 코드</label>
    <div class="otp-wrap">
      <input id="code" name="code" type="text"
             inputmode="numeric" autocomplete="one-time-code"
             maxlength="6" pattern="[0-9]{6}" required
             class="otp" title="6자리 숫자를 입력하세요"
             aria-describedby="hint">
    </div>
    <div class="actions">
      <button type="submit" class="btn">등록 완료</button>
      <a href="<%= ctx %>/login.do?method=login" class="link">로그인으로 돌아가기</a>
    </div>
    <p id="hint" class="foot">보안을 위해 코드가 일치하지 않으면 다시 시도해야 합니다.</p>
  </form>
</main>

<script>
(function(){
  var code = document.getElementById('code');
  var form = document.getElementById('enroll-form');
  var btn  = document.getElementById('copy-btn');
  var sec  = document.getElementById('secret');

  if (code){ code.focus(); }

  // 시크릿 복사
  if (btn && sec){
    btn.addEventListener('click', function(){
      var txt = (sec.textContent || '').trim();
      if (!txt) return;
      if (navigator.clipboard && navigator.clipboard.writeText){
        navigator.clipboard.writeText(txt).then(function(){
          btn.textContent = '복사됨';
          setTimeout(function(){ btn.textContent = '복사'; }, 1400);
        });
      } else {
        // 구형 브라우저 대체
        var ta = document.createElement('textarea');
        ta.value = txt; document.body.appendChild(ta);
        ta.select(); document.execCommand('copy'); document.body.removeChild(ta);
        btn.textContent = '복사됨';
        setTimeout(function(){ btn.textContent = '복사'; }, 1400);
      }
    });
  }

  // 숫자만 허용 + 공백 제거 + 6자리 자동 제출(원치 않으면 자동 제출 부분 주석)
  if (code && form){
    code.addEventListener('input', function(){
      var v = code.value.replace(/\s+/g,'').replace(/[^0-9]/g,'').slice(0,6);
      if (v !== code.value) code.value = v;
      if (code.value.length === 6){
        if (form.requestSubmit) form.requestSubmit(); else form.submit();
      }
    });
    code.addEventListener('paste', function(ev){
      ev.preventDefault();
      var t = (ev.clipboardData || window.clipboardData).getData('text') || '';
      t = t.replace(/\s+/g,'').replace(/[^0-9]/g,'').slice(0,6);
      document.execCommand('insertText', false, t);
    });
    code.addEventListener('keydown', function(e){
      var allow = [8,9,13,37,39,46]; // backspace, tab, enter, arrows, delete
      if (allow.indexOf(e.keyCode) >= 0) return;
      var isDigit = (e.keyCode >= 48 && e.keyCode <= 57) || (e.keyCode >= 96 && e.keyCode <= 105);
      if (!isDigit) e.preventDefault();
    });
  }
})();
</script>
</body>
</html>
