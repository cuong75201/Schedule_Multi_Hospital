import { NavLink } from "react-router";

const Footer = () => {
  return (
    <footer className="bg-[#0c2456] py-10 text-[#cbd9f5]">
      <div className="mx-auto flex w-[calc(100%-80px)] max-w-330 items-center justify-between gap-6 text-xs max-[760px]:w-[min(calc(100%-36px),560px)] max-[760px]:flex-col max-[760px]:items-start max-[420px]:w-[calc(100%-32px)]">
        <a className="inline-flex shrink-0 items-center gap-2.75 text-lg font-extrabold tracking-[-.04em] whitespace-nowrap text-white no-underline" href="#dau-trang" aria-label="CarePath, về đầu trang">
          <span className="grid size-7.5 place-items-center rounded-[11px_11px_11px_3px] bg-lime text-blue-deep [&_svg]:size-5.25" aria-hidden="true">
            <svg viewBox="0 0 24 24" fill="none"><path d="M12 3v18M3 12h18" stroke="currentColor" strokeWidth="3" /></svg>
          </span>
          CarePath
        </a>
        <nav className="flex flex-wrap gap-6 [&_a]:no-underline [&_a:hover]:text-white" aria-label="Điều hướng cuối trang">
          <a href="#ve-nen-tang">Về CarePath</a>
          <a href="#cach-dat-lich">Lựa chọn tìm kiếm</a>
          <a href="#khung-gio">Lịch tiếp nhận</a>
          <NavLink to="/#danh-cho-benh-vien" onClick={() => document.getElementById("danh-cho-benh-vien")?.scrollIntoView()}>Dành cho bệnh viện</NavLink>
        </nav>
      </div>
    </footer>
  )
}

export default Footer
